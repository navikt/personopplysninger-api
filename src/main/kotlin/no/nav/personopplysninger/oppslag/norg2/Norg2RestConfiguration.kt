package no.nav.personopplysninger.oppslag.norg2

import com.fasterxml.jackson.databind.ObjectMapper
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
open class Norg2RestConfiguration {

    @Bean
    @Throws(URISyntaxException::class)
    open fun norg2Consumer(
            @Named("norg2Client") client: Client,
            @Value("\${NORG2_API_V1_URL}") norg2ServiceUri: String): Norg2Consumer {
        return Norg2Consumer(client, URI(norg2ServiceUri))
    }

    @Bean
    open fun norg2Client(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .build()
    }
}
