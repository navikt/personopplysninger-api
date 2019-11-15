package no.nav.personopplysninger.features.personalia

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.ext.ContextResolver
import java.net.URI
import java.net.URISyntaxException

@Configuration
open class PersonaliaRestConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_TPS_PROXY_API_V1_INNSYN_APIKEY_PASSWORD}")
    private val tpsProxyApiKeyPassword: String? = null

    @Value("\${PERSONOPPLYSNINGER_API_DKIF_API_APIKEY_PASSWORD}")
    private val dkifApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun personConsumer(
            @Named("tpsProxyClient") client: Client,
            @Value("\${TPS_PROXY_API_V1_INNSYN_URL}") personServiceUri: String): PersonConsumer {
        return PersonConsumer(client, URI(personServiceUri))
    }

    @Bean
    @Throws(URISyntaxException::class)
    open fun kontaktinformasjonConsumer(
            @Named("dkifClient") client: Client,
            @Value("\${DKIF_API_URL}") kontaktinfoServiceUri: String): KontaktinfoConsumer {
        return KontaktinfoConsumer(client, URI(kontaktinfoServiceUri))
    }

    @Bean
    open fun tpsProxyClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.getHeaders()
                            .putSingle(ConsumerFactory.DEFAULT_APIKEY_USERNAME, tpsProxyApiKeyPassword)
                })
                .build()
    }

    @Bean
    open fun dkifClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.getHeaders()
                            .putSingle(ConsumerFactory.DEFAULT_APIKEY_USERNAME, dkifApiKeyPassword)
                })
                .build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
    }
}
