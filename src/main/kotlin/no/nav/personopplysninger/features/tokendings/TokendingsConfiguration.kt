package no.nav.personopplysninger.features.tokendings

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.ext.ContextResolver

@Configuration
open class TokendingsConfiguration {

    @Bean
    @Throws(URISyntaxException::class)
    open fun tokendingsConsumer(
        @Named("tokendingsTokenClient") client: Client
    ): TokendingsConsumer {
        return TokendingsConsumer(client, URI(TokendingsContext().wellKnownUrl))
    }

    @Bean
    open fun tokendingsTokenClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
            .build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
            .register(clientObjectMapperResolver)
    }
}
