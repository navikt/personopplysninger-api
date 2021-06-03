package no.nav.personopplysninger.features.tokendings.metadata

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.tokendings.TokendingsContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.ext.ContextResolver

@Configuration
open class TokendingsMetadataConfiguration {

    @Bean
    @Throws(URISyntaxException::class)
    open fun tokendingsMetaDataConsumer(
        @Named("tokendingsMetaDataClient") client: Client): TokendingsMetadataConsumer {
        return TokendingsMetadataConsumer(client, URI(TokendingsContext().wellKnownUrl))
    }

    @Bean
    open fun tokendingsMetaDataClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
            .build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
            .register(clientObjectMapperResolver)
    }
}
