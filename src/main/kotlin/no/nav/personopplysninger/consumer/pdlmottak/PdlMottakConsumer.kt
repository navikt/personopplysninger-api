package no.nav.personopplysninger.consumer.pdlmottak

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.config.HEADER_NAV_PERSONIDENT
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.PersonEndring
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Personopplysning
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Personopplysning.Companion.endreTelefonnummerPayload
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Personopplysning.Companion.slettKontaktadressePayload
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Personopplysning.Companion.slettTelefonnummerPayload
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.consumer.pdlmottak.dto.outbound.Endring
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.LoggerFactory
import java.util.*

private const val SLEEP_TIME_MS = 1000L
private const val MAX_POLLS = 5
private const val URL_ENDRINGER = "/api/v1/endringer"

class PdlMottakConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {
    private val logger = LoggerFactory.getLogger(PdlMottakConsumer::class.java)

    suspend fun endreTelefonnummer(token: String, fnr: String, telefonnummer: Telefonnummer): Endring {
        val payload = endreTelefonnummerPayload(fnr, telefonnummer)
        return sendPdlEndring(token, payload, fnr)
    }

    suspend fun slettTelefonnummer(token: String, fnr: String, opplysningsId: String): Endring {
        val payload = slettTelefonnummerPayload(fnr, opplysningsId)
        return sendPdlEndring(token, payload, fnr)
    }

    suspend fun slettKontaktadresse(token: String, fnr: String, opplysningsId: String): Endring {
        val payload = slettKontaktadressePayload(fnr, opplysningsId)
        return sendPdlEndring(token, payload, fnr)
    }

    private suspend fun sendPdlEndring(token: String, payload: Personopplysning, fnr: String): Endring {
        val accessToken = tokenDingsService.exchangeToken(token, environment.pdlMottakTargetApp)
        val endpoint = environment.pdlMottakUrl.plus(URL_ENDRINGER)

        val response: HttpResponse =
            client.post(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header(HEADER_NAV_PERSONIDENT, fnr)
                contentType(ContentType.Application.Json)
                setBody(PersonEndring(payload))
            }

        return try {
            readResponseAndPollStatus(accessToken, response)
        } catch (e: Exception) {
            throw RuntimeException("Forsøkte å endre personopplysning. endpoint=[$endpoint].", e)
        }
    }

    private suspend fun readResponseAndPollStatus(accessToken: String, response: HttpResponse): Endring {
        return when {
            response.status == HttpStatusCode.Locked -> {
                logger.info("Oppdatering avvist pga status pending.")
                Endring(statusType = "REJECTED", error = response.body())
            }

            response.status == HttpStatusCode.UnprocessableEntity -> {
                logger.error("Fikk valideringsfeil: ${response.bodyAsText()}")
                Endring(statusType = "ERROR", error = response.body())
            }

            !response.status.isSuccess() -> {
                throw RuntimeException(
                    consumerErrorMessage(environment.pdlMottakUrl, response.status.value, response.body())
                )
            }

            else -> {
                val location = response.headers[HttpHeaders.Location]
                    ?: throw RuntimeException("Fant ikke Location-header i respons fra Pdl-mottak")
                val pollEndringUrl = environment.pdlMottakUrl.plus(location)
                pollEndring(accessToken, pollEndringUrl)
            }
        }
    }

    private suspend fun pollEndring(accessToken: String, url: String): Endring {
        var endring: Endring
        var i = 0
        do {
            try {
                delay(SLEEP_TIME_MS)
            } catch (ie: InterruptedException) {
                throw RuntimeException("Fikk feil under polling på status", ie)
            }

            val response: HttpResponse =
                client.get(url) {
                    header(HEADER_AUTHORIZATION, BEARER + accessToken)
                    header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                    header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                }
            endring = response.body<List<Endring>>().first()
        } while (++i < MAX_POLLS && endring.isPending())
        logger.info("Antall polls for status: $i")

        if (!endring.confirmedOk()) {
            if (endring.hasTpsError()) {
                endring.addValidationError()
            } else {
                logger.warn("Polling timet ut før endring ble bekreftet OK av pdl-mottak")
            }
        }
        return endring
    }
}
