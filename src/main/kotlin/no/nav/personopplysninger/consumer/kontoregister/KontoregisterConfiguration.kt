package no.nav.personopplysninger.consumer.kontoregister

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
open class KontoregisterConfiguration {

    @Value("\${KONTOREGISTER_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun kontoregisterConsumer(
        @Named("kontoregisterClient") client: Client,
        @Value("\${KONTOREGISTER_URL}") kontoregisterUri: String,
        tokenDingsService: TokenDingsService
    ): KontoregisterConsumer {
        return KontoregisterConsumer(client, URI(kontoregisterUri), tokenDingsService, targetApp)
    }

    @Bean
    open fun kontoregisterClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder().register(clientObjectMapperResolver)
    }
}
