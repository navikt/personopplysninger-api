package no.nav.personopplysninger.features.tokenx

import com.fasterxml.jackson.databind.ObjectMapper
import com.natpryce.konfig.*
import kotlinx.coroutines.runBlocking
import no.nav.personopplysninger.consumerutils.DEFAULT_APIKEY_USERNAME
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import no.nav.security.token.support.jaxrs.JwtTokenClientRequestFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.ext.ContextResolver

private val config: com.natpryce.konfig.Configuration =
    ConfigurationProperties.systemProperties() overriding EnvironmentVariables()
private val audience = "dev-gcp:default:personopplysniger-api"

@Configuration
open class TokenXConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_TOKENX_REST_API_APIKEY_PASSWORD}")
    private val medlApiKeyPassword: String? = null

    // todo rename til tokendings ?

    @Bean
    @Throws(URISyntaxException::class)
    open fun tokenXConsumer(
        @Named("tokenXClient") client: Client,
        @Value("\${TOKENX_API_URL}") tokenXUri: String,
        stsConsumer: STSConsumer
    ): TokenXConsumer {
        return TokenXConsumer(client, URI(tokenXUri), stsConsumer)
    }

    @Bean
    open fun tokenXClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
            .register(ClientRequestFilter { requestContext ->
                requestContext.headers
                    .putSingle(DEFAULT_APIKEY_USERNAME, medlApiKeyPassword)
            })
            .build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
            .register(JwtTokenClientRequestFilter::class.java)
            .register(clientObjectMapperResolver)
    }

    // todo refactor
    data class TokenXConfig(
        val wellKnownUrl: String = config[Key("token.x.well.known.url", stringType)],
        val clientId: String = config[Key("token.x.client.id", stringType)],
        val privateJwk: String = config[Key("token.x.private.jwk", stringType)],
        val gcpAudience: String = config.getOrElse(Key("client.gcp.audience", stringType), audience),
        val onpremAudience: String = config.getOrElse(Key("client.onprem.audience", stringType), audience)
    ) {
        val metadata: OauthServerConfigurationMetadata =
            // todo metadata fra api
            runBlocking {
                OauthServerConfigurationMetadata(
                    issuer = "https://tokendings.dev-gcp.nais.io",
                    authorizationEndpoint = "https://tokendings.dev-gcp.nais.io/authorization",
                    tokenEndpoint = "https://tokendings.dev-gcp.nais.io/token",
                    jwksUri = "https://tokendings.dev-gcp.nais.io/jwks"
                )
            }
    }
}
