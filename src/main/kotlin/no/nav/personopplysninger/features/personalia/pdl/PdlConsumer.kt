package no.nav.personopplysninger.features.personalia.pdl

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.CONSUMER_ID
import no.nav.personopplysninger.consumerutils.ConsumerException
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPersonInfo
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlResponse
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.createKontaktadresseRequest
import no.nav.personopplysninger.features.personalia.pdl.dto.createPersonInfoRequest
import no.nav.personopplysninger.features.personalia.pdl.dto.error.PDLErrorType
import no.nav.personopplysninger.features.personalia.pdl.dto.error.PdlErrorResponse
import no.nav.personopplysninger.features.personalia.pdl.dto.telefon.createTelefonRequest
import no.nav.personopplysninger.oppslag.sts.STSConsumer
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

    // Defer to getTelefonInfo until more information is fetched from PDL
    fun getPersonInfo(ident: String): PdlPersonInfo {
        return postPersonQuery(createPersonInfoRequest(ident))
    }

    fun getKontaktadresseInfo(ident: String): PdlPersonInfo {
        return postPersonQuery(createKontaktadresseRequest(ident))
    }

    fun getTelefonInfo(ident: String): PdlPersonInfo {
        return postPersonQuery(createTelefonRequest(ident))
    }

    private fun postPersonQuery(request: PDLRequest): PdlPersonInfo {
        var response: Response? = null
        return try {
            response = buildRequest()
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON))

            val responseObject: PdlResponse = response.unmarshalBody()
            responseObject.data.person
        } catch (e: ConsumerException) {
            logErrorResponse(response, e)
            emptyData
        }
    }

    private fun logErrorResponse(response: Response?, exception: ConsumerException) {
        if (response != null) {
            val errorResponse: PdlErrorResponse = response.unmarshalBody()

            when (errorResponse.errors.first().errorType) {
                PDLErrorType.NOT_FOUND -> log.warn("Fant ikke bruker i PDL.")
                PDLErrorType.NOT_AUTHENTICATED -> log.warn("Autentiseringsfeil mot PDL. Feil i brukertoken eller systemtoken.")
                PDLErrorType.ABAC_ERROR -> log.warn("Systembruker har ikke tilgang til opplysning")
                PDLErrorType.UNKNOWN_ERROR -> log.warn("Ukjent feil: ${exception.printStackTrace()}")
            }
        } else {
            log.warn("Tomt svar fra PDL")
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

    private val emptyData: PdlPersonInfo get() = PdlPersonInfo()

    private val systemToken: String get() = stsConsumer.token.access_token
}