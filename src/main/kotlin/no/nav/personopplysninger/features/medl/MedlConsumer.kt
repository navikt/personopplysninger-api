package no.nav.personopplysninger.features.medl

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.features.tokendings.TokenDingsService
import no.nav.personopplysninger.util.*
import no.nav.personopplysninger.util.JsonDeserialize.objectMapper
import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder
import org.eclipse.jetty.http.HttpHeader
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class MedlConsumer constructor(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService,
    private val targetApp: String?
) {
    fun hentMedlemskap(fnr: String): Medlemskapsunntak {
        val tokendingsToken = tokenDingsService.exchangeToken(getToken(), targetApp)
        getBuilder(fnr, tokendingsToken.accessToken).get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return objectMapper.readValue(responseBody)
        }
    }

    private fun getBuilder(fnr: String, accessToken: String): Invocation.Builder {
        return client.target(endpoint)
            .path("api/v1/innsyn/person")
            .request()
            .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
            .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            .header(HEADER_NAV_PERSONIDENT_KEY, fnr)
            .header(HttpHeader.ACCEPT.asString(), MediaType.APPLICATION_JSON)
            .header(HttpHeader.AUTHORIZATION.asString(), "Bearer $accessToken")
    }

    private fun getToken(): String {
        val claimsIssuer = "selvbetjening"
        val context = JaxrsTokenValidationContextHolder.getHolder()
        return context.tokenValidationContext.getJwtToken(claimsIssuer).tokenAsString
    }

}
