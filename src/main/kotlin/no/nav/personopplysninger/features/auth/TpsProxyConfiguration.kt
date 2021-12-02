package no.nav.personopplysninger.features.auth

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.consumerutils.DEFAULT_APIKEY_USERNAME
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

@Configuration
open class TpsProxyConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_TPS_PROXY_API_APIKEY_PASSWORD}")
    private val tpsProxyApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun tpsProxyNameConsumer(
            @Named("tpsProxyNameClient") client: Client,
            @Value("\${TPS_PROXY_API_V1_NAVN_URL}") personServiceUri: String): TpsProxyNameConsumer {
        return TpsProxyNameConsumer(client, URI(personServiceUri))
    }

    @Bean
    open fun tpsProxyNameClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
                .register(JwtTokenClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, tpsProxyApiKeyPassword)
                })
                .build()
    }
}
