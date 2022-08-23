package no.nav.personopplysninger.consumer.pdlmottak

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.consumer.BEARER
import no.nav.personopplysninger.consumer.CONSUMER_ID
import no.nav.personopplysninger.consumer.HEADER_AUTHORIZATION
import no.nav.personopplysninger.consumer.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.consumer.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.consumer.HEADER_NAV_PERSONIDENT
import no.nav.personopplysninger.consumer.JsonDeserialize.objectMapper
import no.nav.personopplysninger.consumer.pdlmottak.dto.Endring
import no.nav.personopplysninger.consumer.pdlmottak.dto.Personopplysning
import no.nav.personopplysninger.consumer.tokendings.TokenDingsService
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.personopplysninger.util.getJson
import no.nav.personopplysninger.util.getToken
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.HttpMethod
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

private const val HTTP_CODE_422 = 422
private const val HTTP_CODE_423 = 423
private const val SLEEP_TIME_MS = 1000L
private const val MAX_POLLS = 3
private const val URL_ENDRINGER = "/api/v1/endringer"

class PdlMottakConsumer(
    private val client: Client,
    private val endpoint: URI,
    private var tokenDingsService: TokenDingsService,
    private var targetApp: String?
) {
    private val log = LoggerFactory.getLogger(PdlMottakConsumer::class.java)

    fun endreTelefonnummer(fnr: String, endreTelefon: Personopplysning): Endring {
        return sendPdlEndring(endreTelefon, fnr)
    }

    fun slettPersonopplysning(fnr: String, opphoerPersonopplysning: Personopplysning, ): Endring {
        return sendPdlEndring(opphoerPersonopplysning, fnr)
    }

    private fun sendPdlEndring(entitetSomEndres: Personopplysning, fnr: String): Endring {
        val request = buildEndreRequest(fnr)

        val entity = Entity.entity(entitetSomEndres.asSingleEndring(), MediaType.APPLICATION_JSON)

        return try {
            request.method(
                HttpMethod.POST, entity
            ).use { response ->
                readResponseAndPollStatus(response)
            }
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun readResponseAndPollStatus(response: Response): Endring {
        val responseBody = response.readEntity(String::class.java)
        return when {
            response.status == HTTP_CODE_423 -> {
                log.info("Oppdatering avvist pga status pending.")
                Endring(statusType = "REJECTED", error = objectMapper.readValue(responseBody))
            }
            response.status == HTTP_CODE_422 -> {
                log.error("Fikk valideringsfeil: ${getJson(this)}")
                Endring(statusType = "ERROR", error = objectMapper.readValue(responseBody))
            }
            SUCCESSFUL != response.statusInfo.family -> {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            else -> {
                val pollEndringUrl = response.getHeaderString(HttpHeaders.LOCATION)
                buildPollEndringRequest(pollEndringUrl)
                    .pollFor(SLEEP_TIME_MS, MAX_POLLS)
            }
        }
    }

    private fun Invocation.Builder.pollFor(pollInterval: Long, maxPolls: Int): Endring {
        var endring: Endring
        var i = 0
        do {
            try {
                Thread.sleep(pollInterval)
            } catch (ie: InterruptedException) {
                throw ConsumerException("Fikk feil under polling på status", ie)
            }

            val pollResponse = get()
            val endringList = objectMapper.readValue<List<Endring>>(pollResponse.readEntity(String::class.java))
            endring = endringList[0]
        } while (++i < maxPolls && endring.isPending())
        log.info("Antall polls for status: $i")

        if (!endring.confirmedOk()) {
            endring.createValidationErrorIfTpsHasError()
            val json = runCatching {
                ObjectMapper().writeValueAsString(endring)
            }.getOrDefault("")
            log.warn("${endring.errorMessage()}\n$json")
        }
        return endring
    }

    private fun buildEndreRequest(fnr: String): Invocation.Builder {
        return getBuilder(URL_ENDRINGER).header(HEADER_NAV_PERSONIDENT, fnr)
    }

    private fun buildPollEndringRequest(url: String): Invocation.Builder {
        return getBuilder(url)
    }

    private fun getBuilder(path: String): Invocation.Builder {
        val selvbetjeningToken = getToken()
        val accessToken = tokenDingsService.exchangeToken(selvbetjeningToken, targetApp).accessToken
        return client.target(endpoint)
            .path(path)
            .request()
            .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
            .header(HEADER_AUTHORIZATION, BEARER + accessToken)
            .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
    }
}
