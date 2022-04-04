package no.nav.personopplysninger.consumer.kontaktinformasjon

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
open class KontaktinfoRestConfiguration {

    @Value("\${DIGDIR_KRR_PROXY_CONSUMER_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun kontaktinformasjonConsumer(
        @Named("digdirKrrClient") client: Client,
        @Value("\${DIGDIR_KRR_PROXY_URL}") kontaktinfoServiceUri: String,
        tokenDingsService: TokenDingsService
    ): KontaktinfoConsumer {
        return KontaktinfoConsumer(client, URI(kontaktinfoServiceUri), tokenDingsService, targetApp)
    }

    @Bean
    open fun digdirKrrClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder().register(clientObjectMapperResolver)
    }
}
