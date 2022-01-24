package no.nav.personopplysninger.features.medl

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.tokendings.TokenDingsService
import no.nav.personopplysninger.util.DEFAULT_APIKEY_USERNAME
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
open class MedlConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_MEDLEMSKAP_MEDL_API_APIKEY_PASSWORD}")
    private val medlApiKeyPassword: String? = null

    @Value("\${MEDL_CONSUMER_TARGET_APP}")
    private val targetApp: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun medlConsumer(
            @Named("medlClient") client: Client,
            @Value("\${MEDLEMSKAP_MEDL_API_URL}") medlUri: String,
            tokenDingsService: TokenDingsService): MedlConsumer {
        return MedlConsumer(client, URI(medlUri), tokenDingsService, targetApp)
    }

    @Bean
    open fun medlClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return clientBuilder(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, medlApiKeyPassword)
                })
                .build()
    }

    private fun clientBuilder(clientObjectMapperResolver: ContextResolver<ObjectMapper>): ClientBuilder {
        return ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
    }
}
