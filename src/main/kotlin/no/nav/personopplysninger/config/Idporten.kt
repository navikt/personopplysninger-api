package no.nav.personopplysninger.config

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
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.oauth2.sdk.id.ClientID
import com.nimbusds.oauth2.sdk.id.Issuer
import com.nimbusds.oauth2.sdk.pkce.CodeChallenge
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier
import com.nimbusds.openid.connect.sdk.Nonce
import com.nimbusds.openid.connect.sdk.claims.ACR
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.takeFrom
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.net.URL
import java.time.Instant
import java.util.Base64
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

data class Idporten(
    val redirectUri: String = System.getenv("AUTH_REDIRECT_URI"),
    val wellKnownUrl: String = System.getenv("IDPORTEN_WELL_KNOWN_URL"),
    val clientId: String = System.getenv("AUTH_CLIENT_ID"),
    val clientJwk: String = System.getenv("AUTH_CLIENT_JWK"),
    val encryptionKey: SecretKey = SecretKeySpec(
        Base64.getDecoder().decode(System.getenv("AUTH_ENCRYPTION_KEY")),
        "AES"
    ),
    val acr: String = "idporten-loa-high",
    val allowedAuthTimeSkewSeconds: Long = 10
) {
    val rsaKey: RSAKey = RSAKey.parse(clientJwk)
    val httpClient = HttpClientBuilder.build()
    val metadata: OauthServerConfigurationMetadata =
        runBlocking {
            httpClient.getOAuthServerConfigurationMetadata(wellKnownUrl)
        }
    val jwkSource: JWKSource<SecurityContext> = JWKSourceBuilder.create<SecurityContext>(URL(metadata.jwksUri))
        .cache(true)
        .rateLimited(false)
        .refreshAheadCache(true)
        .build()
    val jwsKeySelector = JWSVerificationKeySelector(JWSAlgorithm.RS256, jwkSource)
    val idTokenValidator = IDTokenValidator(Issuer(metadata.issuer), ClientID(clientId), jwsKeySelector, null)

    fun authorizeUrl(
        redirectUri: String,
        state: String,
        nonce: String,
        pkce: Pkce,
        clientId: String = this.clientId,
        scope: List<String> = listOf("openid"),
        responseType: String = "code",
        responseMode: String = "query",
        acrValues: String = acr,
    ): Url = URLBuilder().takeFrom(metadata.authorizationEndpoint).apply {
        parameters.append("client_id", clientId)
        parameters.append("response_type", responseType)
        parameters.append("redirect_uri", redirectUri)
        parameters.append("response_mode", responseMode)
        parameters.append("scope", scope.joinToString(" "))
        parameters.append("acr_values", acrValues)
        parameters.append("state", state)
        parameters.append("nonce", nonce)
        parameters.append("code_challenge", pkce.challenge.value)
        parameters.append("code_challenge_method", pkce.method.value)
        parameters.append("prompt", "login")
    }.build()

    suspend fun token(
        code: String,
        codeVerifier: String,
        nonce: String,
    ): String {
        val resp: TokenResponse = httpClient.submitForm(
            url = metadata.tokenEndpoint,
            formParameters = Parameters.build {
                append("grant_type", "authorization_code")
                append("client_id", clientId)
                append("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                append("client_assertion", clientAssertion())
                append("redirect_uri", redirectUri)
                append("code", code)
                append("code_verifier", codeVerifier)
            },
        ).body()

        val claims = idTokenValidator.validate(SignedJWT.parse(resp.idToken), Nonce(nonce))

        if (claims.acr != ACR(acr)) {
            throw RuntimeException("invalid acr")
        }

        if (claims.authenticationTime == null) {
            throw RuntimeException("missing auth_time")
        }

        if (claims.authenticationTime.before(Date.from(Instant.now().minusSeconds(allowedAuthTimeSkewSeconds)))) {
            throw RuntimeException("auth_time too old")
        }

        return resp.accessToken
    }

    fun encrypt(plaintext: String): String =
        JWEObject(
            JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM),
            Payload(plaintext)
        ).also {
            it.encrypt(DirectEncrypter(encryptionKey))
        }.serialize()

    fun decrypt(ciphertext: String): String =
        JWEObject.parse(ciphertext).also {
            it.decrypt(DirectDecrypter(encryptionKey))
        }.payload.toString()

    private fun clientAssertion(): String {
        val now = Date.from(Instant.now())
        return JWTClaimsSet.Builder()
            .issuer(clientId)
            .subject(clientId)
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
}

data class Pkce(
    val verifier: CodeVerifier = CodeVerifier(),
    val method: CodeChallengeMethod = CodeChallengeMethod.S256,
) {
    val challenge: CodeChallenge = CodeChallenge.compute(method, verifier)
}

@Serializable
data class TokenResponse(
    @JsonNames("id_token")
    val idToken: String,
    @JsonNames("access_token")
    val accessToken: String,
    @JsonNames("expires_in")
    val expiresIn: Int,
    @JsonNames("token_type")
    val tokenType: String,
)

@Serializable
data class OauthServerConfigurationMetadata(
    @JsonNames("issuer") val issuer: String,
    @JsonNames("token_endpoint") val tokenEndpoint: String,
    @JsonNames("jwks_uri") val jwksUri: String,
    @JsonNames("authorization_endpoint") var authorizationEndpoint: String = ""
)

internal suspend fun HttpClient.getOAuthServerConfigurationMetadata(url: String): OauthServerConfigurationMetadata {
    return get(url).body<OauthServerConfigurationMetadata>()
}
