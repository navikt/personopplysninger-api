package no.nav.personopplysninger.consumer.pdl

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
open class PdlRestConfiguration {

    @Value("\${PDL_CONSUMER_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun pdlConsumer(
        @Named("pdlCLient") client: Client,
        @Value("\${PDL_API_URL}") pdlUri: String,
        tokenDingsService: TokenDingsService
    ): PdlConsumer {
        return PdlConsumer(client, URI(pdlUri), tokenDingsService, targetApp)
    }

    @Bean
    open fun pdlCLient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder().register(clientObjectMapperResolver)
    }
}
