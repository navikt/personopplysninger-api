package no.nav.personopplysninger.features.medl

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.*
import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.features.tokendings.TokenDingsService
import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder
import org.eclipse.jetty.http.HttpHeader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class MedlConsumer constructor(
        private val client: Client,
        private val endpoint: URI,
        private val tokenDingsService: TokenDingsService
)
{
    var logger: Logger = LoggerFactory.getLogger(javaClass)

    fun hentMedlemskap(fnr: String): Medlemskapsunntak {
        try {
            val targetApp = "dev-fss:team-rocket:medlemskap-medl-api" // todo lag miljøvariabel
            val tokendingsToken = tokenDingsService.exchangeToken(getToken(), targetApp)
            // todo fjern logging
            logger.info("Requestbearer: ${tokendingsToken.accessToken}")
            getBuilder(fnr, tokendingsToken.accessToken)
            val request = getBuilder(fnr, tokendingsToken.accessToken)
            val response = request.get()
            logger.info("Response from medl:  $response")

            if (!SUCCESSFUL.equals(response.statusInfo.family)) {
                val msg = "Forsøkte å konsumere REST-tjenesten medl. endpoint=[$endpoint], HTTP response status=[${response.status}]. "
                throw ConsumerException(msg.plus(response.unmarshalBody()))
            }
            return response.unmarshalBody()
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten medl. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun getBuilder(fnr: String, accessToken: String): Invocation.Builder {
        logger.info("Endpoint: $endpoint")
        return client.target(endpoint)
                .path("api/v1/innsyn/person")
                .request()
                .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                .header(HEADER_NAV_CONSUMER_TOKEN, "Bearer $accessToken")
                .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                .header(HEADER_NAV_PERSONIDENT_KEY, fnr)
                .header(HttpHeader.ACCEPT.asString(), MediaType.APPLICATION_JSON)
    }

    private fun getToken(): String {
        val claimsIssuer = "selvbetjening"
        val context = JaxrsTokenValidationContextHolder.getHolder()
        return context.tokenValidationContext.getJwtToken(claimsIssuer).tokenAsString
    }

}
