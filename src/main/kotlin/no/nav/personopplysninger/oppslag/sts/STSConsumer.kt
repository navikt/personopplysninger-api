package no.nav.personopplysninger.oppslag.sts

import no.nav.personopplysninger.consumerutils.ConsumerException

import javax.ws.rs.ProcessingException
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response
import java.net.URI

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
            request.get().use { response -> return readEntity(TokenDto::class.java, response) }
        } catch (e: Exception) {
            val msg = "Forsøkte å hente STSToken. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }

    }


    private fun <T> readEntity(responsklasse: Class<T>, response: Response): T {
        try {
            return response.readEntity(responsklasse)
        } catch (e: ProcessingException) {
            throw RuntimeException("Prosesseringsfeil på responsobjekt. Responsklasse: " + e.stackTrace + " " + responsklasse.name, e)
        } catch (e: IllegalStateException) {
            throw RuntimeException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.name, e)
        } catch (e: Exception) {
            throw RuntimeException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.name, e)
        }

    }
}
