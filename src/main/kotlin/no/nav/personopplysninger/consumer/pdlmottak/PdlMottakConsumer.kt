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
import no.nav.personopplysninger.consumer.pdlmottak.dto.opphoer.OpphoerPersonopplysning
import no.nav.personopplysninger.consumer.pdlmottak.dto.telefon.EndreTelefon
import no.nav.personopplysninger.consumer.pdlmottak.dto.telefon.EndringTelefon
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

    suspend fun endreTelefonnummer(token: String, fnr: String, endreTelefon: EndreTelefon): EndringTelefon {
        return sendPdlEndring(token, endreTelefon, fnr, URL_ENDRINGER, EndringTelefon::class.java)
    }

    suspend fun <T : Endring<T>> slettPersonopplysning(
        token: String,
        fnr: String,
        opphoerPersonopplysning: OpphoerPersonopplysning,
        endringsType: Class<T>
    ): T {
        return sendPdlEndring(token, opphoerPersonopplysning, fnr, URL_ENDRINGER, endringsType)
    }

    private suspend fun <T, R : Endring<R>> sendPdlEndring(
        token: String,
        entitetSomEndres: Personopplysning<T>,
        fnr: String,
        path: String,
        endringType: Class<R>
    ): R {
        val accessToken = tokenDingsService.exchangeToken(token, environment.kontoregisterTargetApp)
        val endpoint = environment.pdlMottakUrl.plus(path)

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
            readResponseAndPollStatus(accessToken, response, endringType)
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw RuntimeException(msg, e)
        }
    }

    private suspend fun <T : Endring<T>> readResponseAndPollStatus(
        accessToken: String,
        response: HttpResponse,
        clazz: Class<T>
    ): T {
        return when {
            response.status == HttpStatusCode.Locked -> {
                getEndring(clazz, "REJECTED").apply {
                    error = response.body()
                    log.info("Oppdatering avvist pga status pending.")
                }
            }
            response.status == HttpStatusCode.UnprocessableEntity -> {
                getEndring(clazz, "ERROR").apply {
                    error = response.body()
                    log.error("Fikk valideringsfeil: ${getJson(this)}")
                }
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
                pollEndring(clazz, accessToken, pollEndringUrl, SLEEP_TIME_MS, MAX_POLLS)
            }
        }
    }

    private fun <T : Endring<T>> getEndring(clazz: Class<T>, statusType: String): T {
        try {
            return clazz.getDeclaredConstructor().newInstance().apply { this.statusType = statusType }
        } catch (e: Exception) {
            log.error("Fikk exception ved forsøk på å instansiere ${clazz.name}")
            throw RuntimeException(e)
        }
    }

    private suspend fun <T : Endring<T>> pollEndring(
        clazz: Class<T>,
        accessToken: String,
        url: String,
        pollInterval: Long,
        maxPolls: Int
    ): T {
        var endring: T
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
            val type = objectMapper.typeFactory.constructCollectionType(List::class.java, clazz)
            val endringList = objectMapper.readValue<List<T>>(response.body<String>(), type)
            endring = endringList[0]
        } while (++i < maxPolls && endring.isPending)
        log.info("Antall polls for status: $i")

        if (!endring.confirmedOk) {
            endring.createValidationErrorIfTpsHasError()
            val json = runCatching {
                ObjectMapper().writeValueAsString(endring)
            }.getOrDefault("")
            log.warn("${endring.errorMessage}\n$json")
        }
        return endring
    }
}
