package no.nav.personopplysninger.features.medl

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.*
import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.features.tokendings.TokenDingsService
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class MedlConsumer constructor(
        private val client: Client,
        private val endpoint: URI,
        private val stsConsumer: STSConsumer,
        private val tokenDingsService: TokenDingsService
)
{
    var logger: Logger = LoggerFactory.getLogger(javaClass)

    fun hentMedlemskap(fnr: String): Medlemskapsunntak {
        try {

            val targetApp = "dev-fss:team-rocket:medlemskap-medl-api" // todo lag miljøvariabel
//            val tokendingsToken = tokenDingsService.exchangeToken(systemToken, targetApp)
            val tokendingsToken = tokenDingsService.exchangeToken(getToken(), targetApp)

            val response = getBuilder(fnr, tokendingsToken.accessToken).get()
            logger.info(response.toString())

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
//        val endpointDev = "https://medlemskap-medl-api.dev.intern.nav.no/" // todo lag miljøvariabel
        return client.target(endpoint)
                .path("api/v1/innsyn/person")
                .request()
                .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                .header(HEADER_NAV_CONSUMER_TOKEN, "Bearer $accessToken")
                .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                .header(HEADER_NAV_PERSONIDENT_KEY, fnr)
    }

    private val systemToken: String get() = stsConsumer.token.access_token

    private fun getToken(): String {
        val claimsIssuer = "selvbetjening"
        val context = JaxrsTokenValidationContextHolder.getHolder()
        return context.tokenValidationContext.getJwtToken(claimsIssuer).tokenAsString
    }

}
