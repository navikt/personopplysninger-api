package no.nav.personopplysninger.features.personalia.pdl

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.CONSUMER_ID
import no.nav.personopplysninger.consumerutils.ConsumerException
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPersonInfo
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlResponse
import no.nav.personopplysninger.features.personalia.pdl.dto.error.PDLErrorType
import no.nav.personopplysninger.features.personalia.pdl.dto.error.PdlErrorResponse
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class PdlConsumer(private val client: Client, private val endpoint: URI, private val stsConsumer: STSConsumer) {

    val RETT_PERSONOPPLYSNINGER = "RPO"
    val GENERELL = "GEN"

    val log = LoggerFactory.getLogger(PdlConsumer::class.java)

    // Defer to getTelefonInfo until more information is fetched from PDL
    fun getPersonInfo(ident: String): PdlPersonInfo {
        return getTelefonInfo(ident)
    }

    fun getTelefonInfo(ident: String): PdlPersonInfo {
        return postQuery(createTelefonRequest(ident)).data.hentPerson
    }


    private fun postQuery(request: PDLRequest): PdlResponse {
        var response: Response? = null
        return try {
            response = buildRequest()
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON))
            response.unmarshalBody()
        } catch (e: ConsumerException) {
            logErrorResponse(response, e)
            emptyData
        }
    }

    private fun logErrorResponse(response: Response?, exception: ConsumerException) {
        if (response != null) {
            val errorResponse: PdlErrorResponse = response.unmarshalBody()
            val firstError = errorResponse.errors.first().errorType

            when (firstError) {
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

    private val emptyData: PdlResponse get() = PdlResponse(
            PdlData(
                    PdlPersonInfo(
                            telefonnummer = emptyList()
                    )
            )
    )

    private val systemToken: String get() = stsConsumer.token.access_token
}