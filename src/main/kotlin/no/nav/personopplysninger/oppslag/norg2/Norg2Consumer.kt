package no.nav.personopplysninger.oppslag.norg2

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.*
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2Enhet
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2EnhetKontaktinfo
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class Norg2Consumer(private val client: Client, private val endpoint: URI) {

    fun hentEnhet(geografisk: String): Norg2Enhet? {
        val request = buildEnhetRequest(geografisk, "enhet/navkontor")
        try {
            return request.readResponse()
        }catch (e: ConsumerException) {
            throw e
        }
    }

    fun hentKontaktinfo(enhetsnr: String): Norg2EnhetKontaktinfo {
        val request = buildKontaktinfoRequest(enhetsnr, "enhet")
        return request.readResponse()
    }

    private fun buildEnhetRequest(geografisk: String, path: String): Invocation.Builder {
        return client.target(endpoint)
                .path("$path/$geografisk")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
    }

    private fun buildKontaktinfoRequest(enhetsnr: String, path: String): Invocation.Builder {
        return client.target(endpoint)
                .path("$path/$enhetsnr/kontaktinformasjon")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("enhetsnr", enhetsnr)
    }

    private inline fun <reified T> Invocation.Builder.readResponse(): T {
        val response = get()
        if (SUCCESSFUL != response.statusInfo.family) {
            val msg = "Forsøkte å konsumere REST-tjenesten Norg2. endpoint=[$endpoint], HTTP response status=[${response.status}]. - "
            throw ConsumerException(msg.plus(response.unmarshalBody()))
        } else {
            return response.unmarshalBody()
        }
    }
}
