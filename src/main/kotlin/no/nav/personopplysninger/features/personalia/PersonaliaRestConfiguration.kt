package no.nav.personopplysninger.features.personalia

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.personalia.pdl.PdlConsumer
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
open class PersonaliaRestConfiguration {

    @Value("\${DIGDIR_KRR_PROXY_CONSUMER_TARGET_APP}")
    private val digdirTargetApp: String? = null

    @Value("\${PDL_CONSUMER_TARGET_APP}")
    private val pdlTargetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun personConsumer(
        @Named("tpsProxyClient") client: Client,
        @Value("\${TPS_PROXY_API_V1_INNSYN_URL}") personServiceUri: String
    ): PersonConsumer {
        return PersonConsumer(client, URI(personServiceUri))
    }

    @Bean
    @Throws(URISyntaxException::class)
    open fun kontaktinformasjonConsumer(
        @Named("digdirKrrClient") client: Client,
        @Value("\${DIGDIR_KRR_PROXY_URL}") kontaktinfoServiceUri: String,
        tokenDingsService: TokenDingsService
    ): KontaktinfoConsumer {
        return KontaktinfoConsumer(client, URI(kontaktinfoServiceUri), tokenDingsService, digdirTargetApp)
    }

    @Bean
    open fun tpsProxyClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    @Bean
    @Throws(URISyntaxException::class)
    open fun pdlConsumer(
        @Named("pdlCLient") client: Client,
        @Value("\${PDL_API_URL}") pdlUri: String,
        tokenDingsService: TokenDingsService
    ): PdlConsumer {
        return PdlConsumer(client, URI(pdlUri), tokenDingsService, pdlTargetApp)
    }

    @Bean
    open fun pdlCLient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    @Bean
    open fun digdirKrrClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder().register(clientObjectMapperResolver)
    }
}
