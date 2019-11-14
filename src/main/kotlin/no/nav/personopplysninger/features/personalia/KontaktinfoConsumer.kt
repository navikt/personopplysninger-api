package no.nav.personopplysninger.features.personalia

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk
import no.nav.log.MDCConstants
import no.nav.personopplysninger.features.ConsumerException
import no.nav.personopplysninger.features.ConsumerFactory
import org.slf4j.MDC

import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response
import java.net.URI

import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class KontaktinfoConsumer(private val client: Client, private val endpoint: URI) {

    fun hentKontaktinformasjon(fnr: String): DigitalKontaktinfoBolk {
        val request = buildRequest(fnr)
        return hentKontaktinformasjon(request)
    }

    private fun buildRequest(fnr: String): Invocation.Builder {
        return client.target(endpoint)
                .path("v1/personer/kontaktinformasjon")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
                .header("Nav-Personidenter", fnr)
    }

    private fun hentKontaktinformasjon(request: Invocation.Builder): DigitalKontaktinfoBolk {
        try {
            request.get()
                    .use { response -> return readResponse(response) }
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten DKIF. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }

    }

    private fun readResponse(r: Response): DigitalKontaktinfoBolk {
        if (SUCCESSFUL != r.statusInfo.family) {
            val msg = "Forsøkte å konsumere REST-tjenesten DKIF. endpoint=[" + endpoint + "], HTTP response status=[" + r.status + "]."
            throw ConsumerException(msg + " - " + ConsumerFactory.readEntity(String::class.java, r))
        } else {
            return ConsumerFactory.readEntity(DigitalKontaktinfoBolk::class.java, r)
        }
    }
}
