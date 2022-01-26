package no.nav.personopplysninger.features.personalia

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.personalia.pdl.PdlConsumer
import no.nav.personopplysninger.features.tokendings.TokenDingsService
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import no.nav.personopplysninger.util.DEFAULT_APIKEY_USERNAME
import no.nav.security.token.support.jaxrs.JwtTokenClientRequestFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.ext.ContextResolver

@Configuration
open class PersonaliaRestConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_TPS_PROXY_API_V1_INNSYN_APIKEY_PASSWORD}")
    private val tpsProxyApiKeyPassword: String? = null

    @Value("\${PERSONOPPLYSNINGER_API_PDL_API_APIKEY_PASSWORD}")
    private val pdlApiKeyPassword: String? = null

    @Value("\${DIGDIR_KRR_PROXY_CONSUMER_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun personConsumer(
            @Named("tpsProxyClient") client: Client,
            @Value("\${TPS_PROXY_API_V1_INNSYN_URL}") personServiceUri: String): PersonConsumer {
        return PersonConsumer(client, URI(personServiceUri))
    }

    @Bean
    @Throws(URISyntaxException::class)
    open fun kontaktinformasjonConsumer(
        @Named("digdirKrrClient") client: Client,
        @Value("\${DIGDIR_KRR_PROXY_URL}") kontaktinfoServiceUri: String,
        tokenDingsService: TokenDingsService): KontaktinfoConsumer {
        return KontaktinfoConsumer(client, URI(kontaktinfoServiceUri), tokenDingsService, targetApp)
    }

    @Bean
    open fun tpsProxyClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, tpsProxyApiKeyPassword)
                })
                .build()
    }

    @Bean
    @Throws(URISyntaxException::class)
    open fun pdlConsumer(@Named("pdlCLient") client: Client,
                         @Value("\${PDL_API_URL}") pdlUri: String,
                         stsConsumer: STSConsumer): PdlConsumer {
        return PdlConsumer(client, URI(pdlUri), stsConsumer)
    }

    @Bean
    open fun pdlCLient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, pdlApiKeyPassword)
                })
                .build()
    }

    @Bean
    open fun digdirKrrClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver).build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
                .register(JwtTokenClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
    }
}
