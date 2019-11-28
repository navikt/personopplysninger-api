package no.nav.personopplysninger.oppslag.sts

import no.nav.personopplysninger.consumerutils.ConsumerException
import no.nav.personopplysninger.consumerutils.unmarshalBody
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation

class STSConsumer(private val client: Client, private val endpoint: URI) {

    val token: TokenDto
        get() {
            val request = buildSTSRequest()
            return getToken(request)
        }

    private fun getBuilder(path: String): Invocation.Builder {
        return client.target(endpoint)
                .path(path)
                .queryParam("grant_type", "client_credentials")
                .queryParam("scope", "openid")
                .request()
    }

    private fun buildSTSRequest(): Invocation.Builder {
        return getBuilder("")
    }

    private fun getToken(request: Invocation.Builder): TokenDto {
        try {
            request.get().use { response -> return response.unmarshalBody() }
        } catch (e: Exception) {
            val msg = "Forsøkte å hente STSToken. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }
}
