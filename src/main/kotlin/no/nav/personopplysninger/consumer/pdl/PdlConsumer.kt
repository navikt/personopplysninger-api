package no.nav.personopplysninger.consumer.pdl

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.consumer.*
import no.nav.personopplysninger.consumer.JsonDeserialize.objectMapper
import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.consumer.pdl.dto.PdlPerson
import no.nav.personopplysninger.consumer.pdl.dto.PdlResponse
import no.nav.personopplysninger.consumer.pdl.request.*
import no.nav.personopplysninger.consumer.tokendings.TokenDingsService
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.personopplysninger.util.getToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val RETT_PERSONOPPLYSNINGER = "RPO"

class PdlConsumer(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService,
    private val targetApp: String?
) {
    val log: Logger = LoggerFactory.getLogger(PdlConsumer::class.java)

    fun getPersonInfo(ident: String): PdlData {
        return postPersonQuery(createPersonInfoRequest(ident))
    }

    fun getKontaktadresseInfo(ident: String): PdlPerson {
        return postPersonQuery(createKontaktadresseRequest(ident)).person!!
    }

    fun getTelefonInfo(ident: String): PdlPerson {
        return postPersonQuery(createTelefonRequest(ident)).person!!
    }

    fun getNavn(ident: String): PdlPerson {
        return postPersonQuery(createNavnRequest(ident)).person!!
    }

    private fun postPersonQuery(request: PDLRequest): PdlData {
        buildRequest().post(Entity.entity(request, MediaType.APPLICATION_JSON)).use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (Response.Status.Family.SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return objectMapper
                .readValue<PdlResponse>(responseBody)
                .data
        }
    }

    private fun buildRequest(): Invocation.Builder {
        val selvbetjeningToken = getToken()
        val accessToken = tokenDingsService.exchangeToken(selvbetjeningToken, targetApp).accessToken
        return client.target(endpoint)
            .request()
            .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
            .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            .header(HEADER_AUTHORIZATION, BEARER + accessToken)
            .header("Tema", RETT_PERSONOPPLYSNINGER)
    }
}