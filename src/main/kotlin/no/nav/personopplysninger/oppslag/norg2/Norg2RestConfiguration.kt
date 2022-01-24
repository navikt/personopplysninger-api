package no.nav.personopplysninger.oppslag.norg2

import com.fasterxml.jackson.databind.ObjectMapper
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
open class Norg2RestConfiguration {

    @Value("\${PERSONOPPLYSNINGER_API_NORG2_API_V1_APIKEY_PASSWORD}")
    private val norg2ApiKeyPassword: String? = null

    @Bean
    @Throws(URISyntaxException::class)
    open fun norg2Consumer(
            @Named("norg2Client") client: Client,
            @Value("\${NORG2_API_V1_URL}") norg2ServiceUri: String): Norg2Consumer {
        return Norg2Consumer(client, URI(norg2ServiceUri))
    }

    @Bean
    open fun norg2Client(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
                .register(JwtTokenClientRequestFilter::class.java)
                .register(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.headers
                            .putSingle(DEFAULT_APIKEY_USERNAME, norg2ApiKeyPassword)
                })
                .build()
    }
}
