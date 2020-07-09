package no.nav.personopplysninger.features.endreopplysninger

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.consumerutils.DEFAULT_APIKEY_USERNAME
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter
import org.slf4j.LoggerFactory
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
open class PersonMottakConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_PERSON_MOTTAK_API_V1_APIKEY_PASSWORD}")
    private val personMottakApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun personMottakConsumer(
            @Named("personMottakClient") client: Client,
            @Value("\${PERSON_MOTTAK_API_URL}") personMottakServiceUri: String,
            stsConsumer: STSConsumer): PersonMottakConsumer {
        return PersonMottakConsumer(client, URI(personMottakServiceUri), stsConsumer)
    }

    @Bean
    open fun personMottakClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.getHeaders()
                            .putSingle(DEFAULT_APIKEY_USERNAME, personMottakApiKeyPassword)
                })
                .build()
    }
}
