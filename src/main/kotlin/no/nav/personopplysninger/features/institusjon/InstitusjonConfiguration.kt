package no.nav.personopplysninger.features.institusjon

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.tokendings.TokenDingsService
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
open class InstitusjonConfiguration {

    @Value("\${PERSONOPPLYSNINGER_PROXY_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun institusjonConsumer(
        @Named("institusjonClient") client: Client,
        @Value("\${INST2_API_URL}") institusjonUri: String,
        tokenDingsService: TokenDingsService
    ): InstitusjonConsumer {
        return InstitusjonConsumer(client, URI(institusjonUri), tokenDingsService, targetApp)
    }

    @Bean
    open fun institusjonClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder().register(clientObjectMapperResolver)
    }
}
