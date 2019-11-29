package no.nav.personopplysninger.oppslag.sts

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.consumerutils.DEFAULT_APIKEY_USERNAME
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Named
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.ext.ContextResolver
import javax.xml.bind.DatatypeConverter

@Configuration
open class STSTokenRestConfiguration {

    private val BASIC = "BASIC "
    private val AUTHORIZATION = "Authorization"

    @Value("\${SRVPERSONOPPLYSNINGER_API_USERNAME}")
    private val STSUsername: String? = null

    @Value("\${SRVPERSONOPPLYSNINGER_API_PASSWORD}")
    private val STSPassword: String? = null

    @Value("\${PERSONOPPLYSNINGER_API_SECURITY_TOKEN_SERVICE_TOKEN_APIKEY_PASSWORD}")
    private val STSApiKeyPassword: String? = null

    private val basicAuthentication: String
        get() {
            val token = "$STSUsername:$STSPassword"
            try {
                return BASIC + DatatypeConverter.printBase64Binary(token.toByteArray(charset("UTF-8")))
            } catch (ex: UnsupportedEncodingException) {
                throw IllegalStateException("Cannot encode with UTF-8", ex)
            }
        }

    @Bean
    @Throws(URISyntaxException::class)
    open fun stsConsumer(
            @Named("STSClient") client: Client,
            @Value("\${SECURITY_TOKEN_SERVICE_TOKEN_URL}") STSServiceUri: String): STSConsumer {
        return STSConsumer(client, URI(STSServiceUri))
    }

    @Bean
    open fun STSClient(clientObjectMapperResolver: ContextResolver<ObjectMapper>): Client {
        return ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .register(ClientRequestFilter { requestContext ->
                    requestContext.getHeaders().put(AUTHORIZATION, listOf<Any>(basicAuthentication))
                })
                .register(ClientRequestFilter { requestContext ->
                    requestContext.getHeaders().putSingle(DEFAULT_APIKEY_USERNAME, STSApiKeyPassword)
                })
                .build()
    }
}
