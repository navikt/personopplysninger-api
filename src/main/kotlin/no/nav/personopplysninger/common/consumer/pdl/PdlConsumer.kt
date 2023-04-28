package no.nav.personopplysninger.common.consumer.pdl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import no.nav.personopplysninger.common.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.common.consumer.pdl.dto.PdlPerson
import no.nav.personopplysninger.common.consumer.pdl.dto.PdlResponse
import no.nav.personopplysninger.common.consumer.pdl.dto.PdlWarning
import no.nav.personopplysninger.common.consumer.pdl.request.PDLRequest
import no.nav.personopplysninger.common.consumer.pdl.request.createKontaktadresseRequest
import no.nav.personopplysninger.common.consumer.pdl.request.createPersonInfoRequest
import no.nav.personopplysninger.common.consumer.pdl.request.createTelefonRequest
import no.nav.personopplysninger.common.util.consumerErrorMessage
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.LoggerFactory
import java.util.*

private const val HEADER_TEMA = "tema"
private const val HEADER_BEHANDLINGSNUMMER = "behandlingsnummer"
private const val RETT_PERSONOPPLYSNINGER = "RPO"
private const val BEHANDLINGSNUMMER_PERSONOPPLYSNINGER = "B258"

private val logger = LoggerFactory.getLogger(PdlConsumer::class.java)

class PdlConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {
    suspend fun getPersonInfo(token: String, ident: String): PdlData {
        return postPersonQuery(token, createPersonInfoRequest(ident))
    }

    suspend fun getKontaktadresseInfo(token: String, ident: String): PdlPerson {
        return postPersonQuery(token, createKontaktadresseRequest(ident)).person
    }

    suspend fun getTelefonInfo(token: String, ident: String): PdlPerson {
        return postPersonQuery(token, createTelefonRequest(ident)).person
    }

    private suspend fun postPersonQuery(token: String, request: PDLRequest): PdlData {
        val accessToken = tokenDingsService.exchangeToken(token, environment.pdlTargetApp)
        val endpoint = environment.pdlUrl

        val response: HttpResponse =
            client.post(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header(HEADER_TEMA, RETT_PERSONOPPLYSNINGER)
                header(HEADER_BEHANDLINGSNUMMER, BEHANDLINGSNUMMER_PERSONOPPLYSNINGER)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        if (response.status.isSuccess()) {
            val responseBody = response.body<PdlResponse>()
            val warnings = responseBody.extensions?.warnings;
            if (!warnings.isNullOrEmpty()) {
                logWarnings(warnings)
            }
            return responseBody.data
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }

    private fun logWarnings(warnings: List<PdlWarning>) {
        warnings.forEach {
            try {
                logger.warn("Advarsel fra PDL: ${it.message}. Detaljer: ${it.details}.")
            } catch (e: Exception) {
                logger.warn("Fikk advarsel fra PDL (deserialisering av advarsel feilet)")
            }
        }
    }
}