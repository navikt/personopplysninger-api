package no.nav.personopplysninger.common.pdl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import no.nav.personopplysninger.common.pdl.dto.PdlData
import no.nav.personopplysninger.common.pdl.dto.PdlPerson
import no.nav.personopplysninger.common.pdl.dto.PdlResponse
import no.nav.personopplysninger.common.pdl.request.PDLRequest
import no.nav.personopplysninger.common.pdl.request.createKontaktadresseRequest
import no.nav.personopplysninger.common.pdl.request.createPersonInfoRequest
import no.nav.personopplysninger.common.pdl.request.createTelefonRequest
import no.nav.personopplysninger.common.util.consumerErrorMessage
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import java.util.*

private const val RETT_PERSONOPPLYSNINGER = "RPO"

class PdlConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {
    suspend fun getPersonInfo(token: String, ident: String): PdlData {
        return postPersonQuery(token, createPersonInfoRequest(ident))
    }

    suspend fun getKontaktadresseInfo(token: String, ident: String): PdlPerson {
        return postPersonQuery(token, createKontaktadresseRequest(ident)).person!!
    }

    suspend fun getTelefonInfo(token: String, ident: String): PdlPerson {
        return postPersonQuery(token, createTelefonRequest(ident)).person!!
    }

    private suspend fun postPersonQuery(token: String, request: PDLRequest): PdlData {
        val accessToken = tokenDingsService.exchangeToken(token, environment.kontoregisterTargetApp)
        val endpoint = environment.pdlUrl

        val response: HttpResponse =
            client.post(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header("Tema", RETT_PERSONOPPLYSNINGER)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        return if (response.status.isSuccess()) {
            response.body<PdlResponse>().data
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}