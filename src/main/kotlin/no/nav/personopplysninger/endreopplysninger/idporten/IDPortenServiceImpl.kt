package no.nav.personopplysninger.endreopplysninger.idporten

import com.nimbusds.jose.EncryptionMethod
import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWEAlgorithm
import com.nimbusds.jose.JWEHeader
import com.nimbusds.jose.JWEObject
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.Payload
import com.nimbusds.jose.crypto.DirectDecrypter
import com.nimbusds.jose.crypto.DirectEncrypter
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.jwk.source.JWKSourceBuilder
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jose.util.DefaultResourceRetriever
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.oauth2.sdk.id.ClientID
import com.nimbusds.oauth2.sdk.id.Issuer
import com.nimbusds.openid.connect.sdk.Nonce
import com.nimbusds.openid.connect.sdk.claims.ACR
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.http.Cookie
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.server.routing.RoutingRequest
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.nav.personopplysninger.config.IDPortenConfig
import no.nav.personopplysninger.utils.getFnrFromToken
import java.net.URI
import java.time.Instant
import java.util.*

class IDPortenServiceImpl(
    private val config: IDPortenConfig, private val httpClient: HttpClient
) : IDPortenService {
    private val rsaKey: RSAKey = RSAKey.parse(config.clientJwk)
    private val metadata: OauthServerConfigurationMetadata =
        runBlocking {
            httpClient.getOAuthServerConfigurationMetadata(config.wellKnownUrl)
        }
    private val jwkSource: JWKSource<SecurityContext> =
        JWKSourceBuilder.create<SecurityContext>(URI(metadata.jwksUri).toURL(), DefaultResourceRetriever(3000, 3000))
            .cache(true)
            .rateLimited(false)
            .refreshAheadCache(true)
            .build()
    private val jwsKeySelector = JWSVerificationKeySelector(JWSAlgorithm.RS256, jwkSource)
    private val idTokenValidator =
        IDTokenValidator(Issuer(metadata.issuer), ClientID(config.clientId), jwsKeySelector, null)
    private val allowedLocales = setOf("nb", "nn", "en")

    override fun frontendUriWithLocale(locale: String) = URLBuilder().takeFrom(config.frontendUri).apply {
        appendPathSegments(allowedLocales.firstOrNull { it == locale } ?: "nb", "endre-kontonummer")
    }.build()

    override fun createCookie(
        endreKontonummerState: EndreKontonummerState
    ) = Cookie(
        name = "endreKontonummerState",
        value = encrypt(Json.encodeToString(endreKontonummerState)),
        httpOnly = true,
        secure = config.secureCookie,
        extensions = mapOf("SameSite" to "Lax"),
    )

    override fun createExpiredCookie() = Cookie(
        name = "endreKontonummerState",
        value = "",
        maxAge = 0,
        expires = GMTDate.START,
        httpOnly = true,
        secure = config.secureCookie,
        extensions = mapOf("SameSite" to "Lax"),
    )

    override fun createParams() = Triple(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        Pkce(),
    )

    override fun createAuthorizeUrl(
        state: String,
        nonce: String,
        pkce: Pkce,
    ): Url = URLBuilder().takeFrom(metadata.authorizationEndpoint).apply {
        parameters.append("client_id", config.clientId)
        parameters.append("response_type", "code")
        parameters.append("redirect_uri", config.redirectUri)
        parameters.append("response_mode", "query")
        parameters.append("scope", "openid")
        parameters.append("acr_values", config.acr)
        parameters.append("state", state)
        parameters.append("nonce", nonce)
        parameters.append("code_challenge", pkce.challenge.value)
        parameters.append("code_challenge_method", pkce.method.value)
        parameters.append("prompt", "login")
    }.build()

    override fun createEndreKontonummerState(encryptedState: String?): EndreKontonummerState {
        encryptedState ?: throw IDPortenException("missing endreKontonummerState cookie")
        return Json.decodeFromString(decrypt(encryptedState))
    }

    override suspend fun validateRequest(
        request: RoutingRequest,
        endreKontonummerState: EndreKontonummerState,
        fnr: String
    ) {
        // TODO - state cookie should have some expiry or validity period
        val actualState = request.queryParameters["state"] ?: throw IDPortenException("no state in callback")
        if (actualState != endreKontonummerState.state) {
            throw IDPortenException("state mismatch")
        }

        val code = request.queryParameters["code"] ?: throw IDPortenException("no code in callback")
        val reauthToken: String = token(code, endreKontonummerState.codeVerifier, endreKontonummerState.nonce)

        val reauthFnr = getFnrFromToken(reauthToken)
        val stateFnr = endreKontonummerState.bruker

        if (listOf(fnr, reauthFnr, stateFnr).distinct().size > 1) {
            throw IDPortenException("fnr mismatch")
        }
    }

    private suspend fun token(
        code: String,
        codeVerifier: String,
        nonce: String,
    ): String {
        val resp: TokenResponse = httpClient.submitForm(
            url = metadata.tokenEndpoint,
            formParameters = Parameters.build {
                append("grant_type", "authorization_code")
                append("client_id", config.clientId)
                append("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                append("client_assertion", clientAssertion())
                append("redirect_uri", config.redirectUri)
                append("code", code)
                append("code_verifier", codeVerifier)
            },
        ).body()

        validateIdToken(resp.idToken, nonce)

        return resp.idToken
    }

    private fun validateIdToken(idToken: String, nonce: String) {
        idTokenValidator.validate(SignedJWT.parse(idToken), Nonce(nonce)).let { claims ->
            when {
                claims.acr != ACR(config.acr) -> throw IDPortenException("invalid acr")
                claims.authenticationTime == null -> throw IDPortenException("missing auth_time")
                claims.authenticationTime.before(
                    Date.from(Instant.now().minusSeconds(config.allowedAuthTimeSkewSeconds))
                ) -> throw IDPortenException("auth_time too old")

                else -> {}
            }
        }
    }

    private fun encrypt(plaintext: String): String =
        JWEObject(
            JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM),
            Payload(plaintext)
        ).also {
            it.encrypt(DirectEncrypter(config.encryptionKey))
        }.serialize()

    private fun decrypt(ciphertext: String): String =
        JWEObject.parse(ciphertext).also {
            it.decrypt(DirectDecrypter(config.encryptionKey))
        }.payload.toString()

    private fun clientAssertion(): String {
        val now = Date.from(Instant.now())
        return JWTClaimsSet.Builder()
            .issuer(config.clientId)
            .subject(config.clientId)
            .audience(metadata.tokenEndpoint)
            .issueTime(now)
            .expirationTime(Date.from(Instant.now().plusSeconds(60)))
            .jwtID(UUID.randomUUID().toString())
            .notBeforeTime(now)
            .build()
            .sign()
            .serialize()
    }

    private fun JWTClaimsSet.sign(): SignedJWT =
        SignedJWT(
            JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(rsaKey.keyID)
                .type(JOSEObjectType.JWT).build(),
            this,
        ).apply {
            sign(RSASSASigner(rsaKey.toPrivateKey()))
        }

    private suspend fun HttpClient.getOAuthServerConfigurationMetadata(url: String): OauthServerConfigurationMetadata {
        return get(url).body<OauthServerConfigurationMetadata>()
    }
}

