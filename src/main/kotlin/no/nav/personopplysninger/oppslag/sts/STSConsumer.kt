package no.nav.personopplysninger.oppslag.sts

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.personopplysninger.util.ConsumerException
import no.nav.personopplysninger.util.JsonDeserialize
import no.nav.personopplysninger.util.consumerErrorMessage
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response

class STSConsumer(private val client: Client, private val endpoint: URI) {

    val token: TokenDto
        get() {
            val request = buildSTSRequest()
            return getToken(request)
        }

    private fun getBuilder(): Invocation.Builder {
        return client.target(endpoint)
            .queryParam("grant_type", "client_credentials")
            .queryParam("scope", "openid")
            .request()
    }

    private fun buildSTSRequest(): Invocation.Builder {
        return getBuilder()
    }

    private fun getToken(request: Invocation.Builder): TokenDto {
        request.get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (Response.Status.Family.SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return JsonDeserialize.objectMapper.readValue(responseBody)
        }
    }
}
