package no.nav.personopplysninger.features.personaliagammel

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.consumerutils.DEFAULT_APIKEY_USERNAME
import no.nav.personopplysninger.features.personaliagammel.pdl.PdlConsumerGammel
import no.nav.personopplysninger.oppslag.sts.STSConsumer
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
open class PersonaliaRestConfigurationGammel {

    @Value("\${PERSONOPPLYSNINGER_API_TPS_PROXY_API_V1_INNSYN_APIKEY_PASSWORD}")
    private val tpsProxyApiKeyPassword: String? = null

    @Value("\${PERSONOPPLYSNINGER_API_DKIF_API_APIKEY_PASSWORD}")
    private val dkifApiKeyPassword: String? = null

    @Value("\${PERSONOPPLYSNINGER_API_PDL_API_APIKEY_PASSWORD}")
    private val pdlApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun personConsumerGammel(
            @Named("tpsProxyClientGammel") client: Client,
            @Value("\${TPS_PROXY_API_V1_INNSYN_URL}") personServiceUri: String): PersonConsumerGammel {
        return PersonConsumerGammel(client, URI(personServiceUri))
    }

    @Bean
    @Throws(URISyntaxException::class)
    open fun kontaktinformasjonConsumerGammel(
            @Named("dkifClientGammel") client: Client,
            @Value("\${DKIF_API_URL}") kontaktinfoServiceUri: String): KontaktinfoConsumerGammel {
        return KontaktinfoConsumerGammel(client, URI(kontaktinfoServiceUri))
    }

    @Bean
    open fun tpsProxyClientGammel(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, tpsProxyApiKeyPassword)
                })
                .build()
    }

    @Bean
    @Throws(URISyntaxException::class)
    open fun pdlConsumerGammel(@Named("pdlCLientGammel") client: Client,
                         @Value("\${PDL_API_URL}") pdlUri: String,
                         stsConsumer: STSConsumer): PdlConsumerGammel {
        return PdlConsumerGammel(client, URI(pdlUri), stsConsumer)
    }

    @Bean
    open fun pdlCLientGammel(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, pdlApiKeyPassword)
                })
                .build()
    }

    @Bean
    open fun dkifClientGammel(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, dkifApiKeyPassword)
                })
                .build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
                .register(JwtTokenClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
    }
}
