package no.nav.personopplysninger.features.endreopplysningergammel

import com.fasterxml.jackson.databind.ObjectMapper
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

@Configuration
open class PersonMottakConfigurationGammel {

    @Value("\${PERSONOPPLYSNINGER_API_PERSON_MOTTAK_API_V1_APIKEY_PASSWORD}")
    private val personMottakApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun personMottakConsumerGammel(
            @Named("personMottakClientGammel") client: Client,
            @Value("\${PERSON_MOTTAK_API_URL}") personMottakServiceUri: String,
            stsConsumer: STSConsumer): PersonMottakConsumerGammel {
        return PersonMottakConsumerGammel(client, URI(personMottakServiceUri), stsConsumer)
    }

    @Bean
    open fun personMottakClientGammel(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
                .register(JwtTokenClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, personMottakApiKeyPassword)
                })
                .build()
    }
}
