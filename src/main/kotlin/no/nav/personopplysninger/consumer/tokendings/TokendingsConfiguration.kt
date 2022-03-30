package no.nav.personopplysninger.consumer.tokendings

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.consumer.tokendings.metadata.TokendingsMetadataConsumer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.ext.ContextResolver

@Configuration
open class TokendingsConfiguration(
    private val tokendingsMetadataConsumer: TokendingsMetadataConsumer
) {

    @Bean
    @Throws(URISyntaxException::class)
    open fun tokendingsConsumer(
        @Named("tokendingsTokenClient") client: Client
    ): TokendingsConsumer {
        val endpoint = tokendingsMetadataConsumer.hentMetadata().tokenEndpoint
        return TokendingsConsumer(client, URI(endpoint))
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
