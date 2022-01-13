package no.nav.personopplysninger.oppslag.norg2

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2Enhet
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.util.CONSUMER_ID
import no.nav.personopplysninger.util.ConsumerException
import no.nav.personopplysninger.util.JsonDeserialize.objectMapper
import no.nav.personopplysninger.util.consumerErrorMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class Norg2Consumer(private val client: Client, private val endpoint: URI) {

    private var logger: Logger = LoggerFactory.getLogger(javaClass)

    fun hentEnhetDersomGyldig(geografisk: String): Norg2Enhet? {
        return try {
            hentEnhet(geografisk)
        } catch (e: ConsumerException) {
            logger.warn("Feil oppstod ved henting av enhet: ", e)
            null
        }
    }

    private fun hentEnhet(geografisk: String): Norg2Enhet? {
        val request = buildEnhetRequest(geografisk)
        return request.readResponse()
    }

    fun hentKontaktinfo(enhetsnr: String): Norg2EnhetKontaktinfo {
        val request = buildKontaktinfoRequest(enhetsnr)
        return request.readResponse()
    }

    private fun buildEnhetRequest(geografisk: String): Invocation.Builder {
        return client.target(endpoint)
            .path("enhet/navkontor/$geografisk")
            .request()
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
    }

    private fun buildKontaktinfoRequest(enhetsnr: String): Invocation.Builder {
        return client.target(endpoint)
            .path("enhet/$enhetsnr/kontaktinformasjon")
            .request()
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
            .header("enhetsnr", enhetsnr)
    }

    private inline fun <reified T> Invocation.Builder.readResponse(): T {
        get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return objectMapper.readValue(responseBody)
        }
    }
}
