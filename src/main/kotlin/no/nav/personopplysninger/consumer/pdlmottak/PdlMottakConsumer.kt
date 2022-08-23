package no.nav.personopplysninger.consumer.pdlmottak

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.config.HEADER_NAV_PERSONIDENT
import no.nav.personopplysninger.consumer.pdlmottak.dto.Endring
import no.nav.personopplysninger.consumer.pdlmottak.dto.Personopplysning
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.personopplysninger.util.getJson
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.LoggerFactory
import org.slf4j.MDC

private const val SLEEP_TIME_MS = 1000L
private const val MAX_POLLS = 3
private const val URL_ENDRINGER = "/api/v1/endringer"

class PdlMottakConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
    private val objectMapper: ObjectMapper,
) {
    private val log = LoggerFactory.getLogger(PdlMottakConsumer::class.java)

    suspend fun endreTelefonnummer(fnr: String, endreTelefon: Personopplysning): Endring {
        return sendPdlEndring(endreTelefon, fnr)
    }

    suspend fun slettPersonopplysning(fnr: String, opphoerPersonopplysning: Personopplysning): Endring {
        return sendPdlEndring(opphoerPersonopplysning, fnr)
    }

    private suspend fun sendPdlEndring(token: String, entitetSomEndres: Personopplysning, fnr: String, ): Endring {
        val accessToken = tokenDingsService.exchangeToken(token, environment.kontoregisterTargetApp)
        val endpoint = environment.pdlMottakUrl.plus(URL_ENDRINGER)

        val response: HttpResponse =
            client.post(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header(HEADER_NAV_PERSONIDENT, fnr)
                contentType(ContentType.Application.Json)
                setBody(entitetSomEndres.asSingleEndring())
            }

        return try {
            readResponseAndPollStatus(accessToken, response)
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw RuntimeException(msg, e)
        }
    }

    private suspend fun readResponseAndPollStatus(accessToken: String, response: HttpResponse): Endring {
        return when {
            response.status == HttpStatusCode.Locked -> {
                log.info("Oppdatering avvist pga status pending.")
                Endring(statusType = "REJECTED", error = response.body())

            }
            response.status == HttpStatusCode.UnprocessableEntity -> {
                log.error("Fikk valideringsfeil: ${getJson(this)}")
                Endring(statusType = "ERROR", error = response.body())
            }
            !response.status.isSuccess() -> {
                throw RuntimeException(
                    consumerErrorMessage(
                        environment.pdlMottakUrl,
                        response.status.value,
                        response.body()
                    )
                )
            }
            else -> {
                val pollEndringUrl = response.headers[HttpHeaders.Location]!!
                pollEndring(accessToken, pollEndringUrl, SLEEP_TIME_MS, MAX_POLLS)
            }
        }
    }

    private suspend fun pollEndring(accessToken: String, url: String, pollInterval: Long, maxPolls: Int): Endring {
        var endring: Endring
        var i = 0
        do {
            try {
                Thread.sleep(pollInterval)
            } catch (ie: InterruptedException) {
                throw RuntimeException("Fikk feil under polling på status", ie)
            }

            val response: HttpResponse =
                client.get(url) {
                    header(HEADER_AUTHORIZATION, BEARER + accessToken)
                    header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                    header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                }
            val endringList = response.body<List<String>>()
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
}
