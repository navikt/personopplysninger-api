package no.nav.personopplysninger.features.personalia

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.exception.consumerErrorMessage
import no.nav.personopplysninger.util.CONSUMER_ID
import no.nav.personopplysninger.util.JsonDeserialize
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
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
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personidenter", fnr)
    }

    private fun hentKontaktinformasjon(request: Invocation.Builder): DigitalKontaktinfoBolk {
        request.get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return JsonDeserialize.objectMapper.readValue(responseBody)
        }
    }
}
