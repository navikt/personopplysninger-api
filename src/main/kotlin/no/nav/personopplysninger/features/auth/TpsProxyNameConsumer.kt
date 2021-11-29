package no.nav.personopplysninger.features.auth

import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.consumerutils.CONSUMER_ID
import no.nav.personopplysninger.consumerutils.ConsumerException
import no.nav.personopplysninger.consumerutils.unmarshalBody
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class TpsProxyNameConsumer(private val client: Client, private val endpoint: URI) {

    private val log = LoggerFactory.getLogger(TpsProxyNameConsumer::class.java)

    fun hentNavn(fnr: String): Navn {
        val request = buildRequest(fnr)
        return hentNavn(request)
    }

    private fun buildRequest(fnr: String): Invocation.Builder {
        return client.target(endpoint)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", fnr)
    }

    private fun hentNavn(request: Invocation.Builder): Navn {
        try {
            request.get()
                    .use { response -> return readResponse(response) }
        } catch (e: Exception) {
            log.error("Feil oppsto under oppslag på navn, returnerer 200 OK med tomt navn. Feilmelding: ${e.message}")
            return Navn("", "", "")
        }
    }

    private fun readResponse(r: Response): Navn {
        if (SUCCESSFUL != r.statusInfo.family) {
            val msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[$endpoint], HTTP response status=[${r.status}]."
            throw ConsumerException("$msg - ${r.unmarshalBody<String>()}")
        } else {
            return r.unmarshalBody()
        }
    }
}
