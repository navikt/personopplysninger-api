package no.nav.personopplysninger.features.personalia.pdl

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.exception.consumerErrorMessage
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlResponse
import no.nav.personopplysninger.features.personalia.pdl.request.*
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import no.nav.personopplysninger.util.CONSUMER_ID
import no.nav.personopplysninger.util.JsonDeserialize.objectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class PdlConsumer(private val client: Client, private val endpoint: URI, private val stsConsumer: STSConsumer) {

    private val RETT_PERSONOPPLYSNINGER = "RPO"

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
        return client.target(endpoint)
            .path("/graphql")
            .request()
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
            .header("Nav-Consumer-Token", "Bearer $systemToken")
            .header("Tema", RETT_PERSONOPPLYSNINGER)
    }

    private val systemToken: String get() = stsConsumer.token.access_token
}