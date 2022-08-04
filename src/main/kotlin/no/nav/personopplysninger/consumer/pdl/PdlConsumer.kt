package no.nav.personopplysninger.consumer.pdl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.consumer.pdl.dto.PdlPerson
import no.nav.personopplysninger.consumer.pdl.dto.PdlResponse
import no.nav.personopplysninger.consumer.pdl.request.PDLRequest
import no.nav.personopplysninger.consumer.pdl.request.createKontaktadresseRequest
import no.nav.personopplysninger.consumer.pdl.request.createNavnRequest
import no.nav.personopplysninger.consumer.pdl.request.createPersonInfoRequest
import no.nav.personopplysninger.consumer.pdl.request.createTelefonRequest
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

private const val RETT_PERSONOPPLYSNINGER = "RPO"

class PdlConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {
    val log: Logger = LoggerFactory.getLogger(PdlConsumer::class.java)

    suspend fun getPersonInfo(token: String, ident: String): PdlData {
        return postPersonQuery(token, createPersonInfoRequest(ident))
    }

    suspend fun getKontaktadresseInfo(token: String, ident: String): PdlPerson {
        return postPersonQuery(token, createKontaktadresseRequest(ident)).person!!
    }

    suspend fun getTelefonInfo(token: String, ident: String): PdlPerson {
        return postPersonQuery(token, createTelefonRequest(ident)).person!!
    }

    suspend fun getNavn(token: String, ident: String): PdlPerson {
        return postPersonQuery(token, createNavnRequest(ident)).person!!
    }

    private suspend fun postPersonQuery(token: String, request: PDLRequest): PdlData {
        val accessToken = tokenDingsService.exchangeToken(token, environment.kontoregisterTargetApp)
        val endpoint = environment.pdlUrl

        val response: HttpResponse =
            client.post(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header("Tema", RETT_PERSONOPPLYSNINGER)
                setBody(request)
            }
        return if (response.status.isSuccess()) {
            response.body<PdlResponse>().data
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}