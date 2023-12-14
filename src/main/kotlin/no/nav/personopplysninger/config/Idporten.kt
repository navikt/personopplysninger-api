package no.nav.personopplysninger.config

import com.auth0.jwk.JwkProviderBuilder
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.oauth2.sdk.pkce.CodeChallenge
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.time.Instant
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit

data class Idporten(
    val wellKnownUrl: String = System.getenv("IDPORTEN_WELL_KNOWN_URL"),
    val scope: String = "openid",
    val clientId: String = System.getenv("IDPORTEN_CLIENT_ID"),
    // TODO JWK
    val clientJwk: String = System.getenv("IDPORTEN_CLIENT_JWK"),
    val redirectUri: String = "http://localhost:8080/oauth",
) {

    val rsaKey: RSAKey = RSAKey.parse(clientJwk)
    val httpClient = HttpClientBuilder.build()

    val metadata: OauthServerConfigurationMetadata =
        runBlocking {
            httpClient.getOAuthServerConfigurationMetadata(wellKnownUrl)
        }
    val jwkProvider = JwkProviderBuilder(URL(metadata.jwksUri))
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    fun authorizeUrl() {
        metadata.authorizationEndpoint

    }

    fun Url.authenticationRequest(
        clientId: String = "defautlClient",
        redirectUri: String = "http://defaultRedirectUri",
        scope: List<String> = listOf("openid"),
        responseType: String = "code",
        responseMode: String = "query",
        state: String = "1234",
        //TODO nonce
        nonce: String = "5678",
        acrValues: String = "idporten-loa-high",
        pkce: Pkce = Pkce(),
    ): Url = URLBuilder().apply {
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
    }.build()

    fun token() {

    }

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

    suspend fun getIdToken(
        code: String,
        pkce: Pkce,
    ): AccessToken =
        fetchAccessToken(
            Parameters.build {
                append("grant_type", "authorization_code")
                append("client_id", clientId)
                append("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                append("client_assertion", clientAssertion())
                //append("scope", scope)
                append("redirect_uri", redirectUri)
                append("code", code)
                append("code_verifier", pkce.verifier.value)
            },
        )
    private suspend inline fun fetchAccessToken(formParameters: Parameters): AccessToken =
        httpClient.submitForm(
            url = metadata.tokenEndpoint,
            formParameters = formParameters,
        ).body()


    data class Pkce(
        val verifier: CodeVerifier = CodeVerifier(),
        val method: CodeChallengeMethod = CodeChallengeMethod.S256,
    ) {
        val challenge: CodeChallenge = CodeChallenge.compute(method, verifier)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class AccessToken(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("token_type")
    val tokenType: String,
)



@JsonIgnoreProperties(ignoreUnknown = true)
data class OauthServerConfigurationMetadata(
    @JsonProperty(value = "issuer", required = true) val issuer: String,
    @JsonProperty(value = "token_endpoint", required = true) val tokenEndpoint: String,
    @JsonProperty(value = "jwks_uri", required = true) val jwksUri: String,
    @JsonProperty(value = "authorization_endpoint", required = false) var authorizationEndpoint: String = ""
)

internal suspend fun HttpClient.getOAuthServerConfigurationMetadata(url: String): OauthServerConfigurationMetadata {
    return get(url).body<OauthServerConfigurationMetadata>()
}
