package no.nav.personopplysninger.features.personalia

import no.nav.log.MDCConstants
import no.nav.personopplysninger.features.ConsumerException
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.tps.person.Personinfo
import org.slf4j.MDC

import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response
import java.net.URI

import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class PersonConsumer(private val client: Client, private val endpoint: URI) {

    fun hentPersonInfo(fnr: String): Personinfo {
        val request = buildRequest(fnr)
        return hentPersoninfo(request)
    }

    private fun buildRequest(fnr: String): Invocation.Builder {
        return client.target(endpoint)
                .path("person")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
                .header("Nav-Personident", fnr)
    }

    private fun hentPersoninfo(request: Invocation.Builder): Personinfo {
        try {
            request.get()
                    .use { response -> return readResponse(response) }
        } catch (e: ConsumerException) {
            throw e
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun readResponse(r: Response): Personinfo {
        if (SUCCESSFUL != r.statusInfo.family) {
            val msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "], HTTP response status=[" + r.status + "]."
            throw ConsumerException(msg + " - " + ConsumerFactory.readEntity(String::class.java, r))
        } else {
            return ConsumerFactory.readEntity(Personinfo::class.java, r)
        }
    }
}
