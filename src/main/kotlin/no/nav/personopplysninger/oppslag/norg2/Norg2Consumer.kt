package no.nav.personopplysninger.oppslag.norg2

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.features.ConsumerException
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2Enhet
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2EnhetKontaktinfo
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class Norg2Consumer(private val client: Client, private val endpoint: URI) {

    fun hentEnhet(geografisk: String): Norg2Enhet {
        val request = buildEnhetRequest(geografisk, "enhet/navkontor")
        return request.readResponse(Norg2Enhet::class.java)
    }

    fun hentKontaktinfo(enhetsnr: String): Norg2EnhetKontaktinfo {
        val request = buildKontaktinfoRequest(enhetsnr, "enhet")
        return request.readResponse(Norg2EnhetKontaktinfo::class.java)
    }

    private fun buildEnhetRequest(geografisk: String, path: String): Invocation.Builder {
        return client.target(endpoint)
                .path("$path/$geografisk")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
    }

    private fun buildKontaktinfoRequest(enhetsnr: String, path: String): Invocation.Builder {
        return client.target(endpoint)
                .path("$path/$enhetsnr/kontaktinformasjon")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
                .header("enhetsnr", enhetsnr)
    }

    private fun <T> Invocation.Builder.readResponse(clazz: Class<T>): T {
        val response = get()
        if (SUCCESSFUL != response.statusInfo.family) {
            val msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[$endpoint], HTTP response status=[${response.status}]. - "
            throw ConsumerException(msg.plus(response.unmarshalBody()))
        } else {
            return ConsumerFactory.readEntity(clazz, response)
        }
    }
}
