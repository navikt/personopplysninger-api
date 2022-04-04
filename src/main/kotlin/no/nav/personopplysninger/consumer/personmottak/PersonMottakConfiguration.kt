package no.nav.personopplysninger.consumer.personmottak

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.consumer.tokendings.TokenDingsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.ext.ContextResolver

@Configuration
open class PersonMottakConfiguration {

    @Value("\${PERSONOPPLYSNINGER_PROXY_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun personMottakConsumer(
        @Named("personMottakClient") client: Client,
        @Value("\${PERSON_MOTTAK_API_URL}") personMottakServiceUri: String,
        tokenDingsService: TokenDingsService
    ): PersonMottakConsumer {
        return PersonMottakConsumer(client, URI(personMottakServiceUri), tokenDingsService, targetApp)
    }

    @Bean
    open fun personMottakClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
            .register(clientObjectMapperResolver)
            .build()
    }
}
