package no.nav.personopplysninger.consumer.tpsproxy

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
open class TpsProxyRestConfiguration {

    @Value("\${PERSONOPPLYSNINGER_PROXY_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun tpsProxyConsumer(
        @Named("tpsProxyClient") client: Client,
        @Value("\${TPS_PROXY_API_V1_INNSYN_URL}") tpsProxyUrl: String,
        tokenDingsService: TokenDingsService
    ): TpsProxyConsumer {
        return TpsProxyConsumer(client, URI(tpsProxyUrl), tokenDingsService, targetApp)
    }

    @Bean
    open fun tpsProxyClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder().register(clientObjectMapperResolver)
    }
}
