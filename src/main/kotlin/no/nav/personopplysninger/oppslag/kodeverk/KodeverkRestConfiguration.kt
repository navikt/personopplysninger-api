package no.nav.personopplysninger.oppslag.kodeverk

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.util.DEFAULT_APIKEY_USERNAME
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
open class KodeverkRestConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_KODEVERK_REST_API_APIKEY_PASSWORD}")
    private val kodeverkApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun kodeverkConsumer(
            @Named("kodeverkClient") client: Client,
            @Value("\${KODEVERK_REST_API_URL}") kodeServiceUri: String): KodeverkConsumer {
        return KodeverkConsumer(client, URI(kodeServiceUri))
    }

    @Bean
    open fun kodeverkClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
                .register(JwtTokenClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, kodeverkApiKeyPassword)
                })
                .build()
    }
}
