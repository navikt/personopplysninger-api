package no.nav.personopplysninger.features.institusjon

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.ext.ContextResolver

@Configuration
open class InstitusjonConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_INST2_REST_API_APIKEY_PASSWORD}")
    private val inst2ApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun institusjonConsumer(
            @Named("institusjonClient") client: Client,
            @Value("\${INST2_API_URL}") institusjonUri: String): InstitusjonConsumer{
        return InstitusjonConsumer(client, URI(institusjonUri))
    }

    @Bean
    open fun institusjonClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register({ requestContext: ClientRequestContext ->
                    object: ClientRequestFilter {
                        override fun filter(requestContext: ClientRequestContext?) {
                            requestContext!!.getHeaders()
                                    .putSingle(ConsumerFactory.DEFAULT_APIKEY_USERNAME, inst2ApiKeyPassword)
                        }
                    }
                })
                .build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
    }
}
