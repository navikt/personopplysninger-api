package no.nav.personopplysninger.consumer.pdlmottak

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.consumer.*
import no.nav.personopplysninger.consumer.JsonDeserialize.objectMapper
import no.nav.personopplysninger.consumer.pdlmottak.domain.Endring
import no.nav.personopplysninger.consumer.pdlmottak.domain.Personopplysning
import no.nav.personopplysninger.consumer.pdlmottak.domain.opphoer.OpphoerPersonopplysning
import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.EndreTelefon
import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.EndringTelefon
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

    fun endreTelefonnummer(fnr: String, endreTelefon: EndreTelefon): EndringTelefon {
        return sendPdlEndring(endreTelefon, fnr, URL_ENDRINGER, EndringTelefon::class.java)
    }

    fun <T : Endring<T>> slettPersonopplysning(
        fnr: String,
        opphoerPersonopplysning: OpphoerPersonopplysning,
        endringsType: Class<T>
    ): T {
        return sendPdlEndring(opphoerPersonopplysning, fnr, URL_ENDRINGER, endringsType)
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

    private fun buildEndreRequest(fnr: String, path: String): Invocation.Builder {
        return getBuilder(path)
            .header(HEADER_NAV_PERSONIDENT, fnr)
    }

    private fun buildPollEndringRequest(url: String): Invocation.Builder {
        return getBuilder(url)
    }

    private fun <T, R : Endring<R>> sendPdlEndring(
        entitetSomEndres: Personopplysning<T>,
        fnr: String,
        path: String,
        endringType: Class<R>
    ): R {

        val request = buildEndreRequest(fnr, path)

        val entity = Entity.entity(entitetSomEndres.asSingleEndring(), MediaType.APPLICATION_JSON)

        return try {
            request.method(
                HttpMethod.POST, entity
            ).use { response ->
                readResponseAndPollStatus(response, endringType)
            }
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun <T : Endring<T>> readResponseAndPollStatus(
        response: Response,
        clazz: Class<T>
    ): T {
        val responseBody = response.readEntity(String::class.java)
        return when {
            response.status == HTTP_CODE_423 -> {
                getEndring(clazz, "REJECTED").apply {
                    error = objectMapper.readValue(responseBody)
                    log.info("Oppdatering avvist pga status pending.")
                }
            }
            response.status == HTTP_CODE_422 -> {
                getEndring(clazz, "ERROR").apply {
                    error = objectMapper.readValue(responseBody)
                    log.error("Fikk valideringsfeil: ${getJson(this)}")
                }
            }
            SUCCESSFUL != response.statusInfo.family -> {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            else -> {
                val pollEndringUrl = response.getHeaderString(HttpHeaders.LOCATION)
                buildPollEndringRequest(pollEndringUrl)
                    .pollFor(clazz, SLEEP_TIME_MS, MAX_POLLS)
            }
        }
    }

    private fun <T : Endring<T>> Invocation.Builder.pollFor(clazz: Class<T>, pollInterval: Long, maxPolls: Int): T {
        var endring: T
        var i = 0
        do {
            try {
                Thread.sleep(pollInterval)
            } catch (ie: InterruptedException) {
                throw ConsumerException("Fikk feil under polling på status", ie)
            }

            val pollResponse = get()
            val type = objectMapper.typeFactory.constructCollectionType(List::class.java, clazz)
            val endringList = objectMapper.readValue<List<T>>(pollResponse.readEntity(String::class.java), type)
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

    private fun <T : Endring<T>> getEndring(clazz: Class<T>, statusType: String): T {
        try {
            return clazz.getDeclaredConstructor().newInstance().apply { this.statusType = statusType }
        } catch (e: Exception) {
            log.error("Fikk exception ved forsøk på å instansiere ${clazz.name}")
            throw RuntimeException(e)
        }
    }
}