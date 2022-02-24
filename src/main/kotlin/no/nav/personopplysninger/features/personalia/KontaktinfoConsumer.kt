package no.nav.personopplysninger.features.personalia

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.exception.consumerErrorMessage
import no.nav.personopplysninger.features.personalia.kontaktinformasjon.DigitalKontaktinformasjon
import no.nav.personopplysninger.features.tokendings.TokenDingsService
import no.nav.personopplysninger.util.CONSUMER_ID
import no.nav.personopplysninger.util.JsonDeserialize
import no.nav.personopplysninger.util.getToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class KontaktinfoConsumer(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService,
    private val targetApp: String?
) {

    private val logger: Logger = LoggerFactory.getLogger(KontaktinfoConsumer::class.java)

    fun hentKontaktinformasjon(fnr: String): DigitalKontaktinformasjon {
        val tokendingsToken = tokenDingsService.exchangeToken(getToken(), targetApp)
        val request = buildRequest(fnr, tokendingsToken.accessToken)
        return hentKontaktinformasjon(request)
    }

    private fun buildRequest(fnr: String, accessToken: String): Invocation.Builder {
        return client.target(endpoint)
            .path("rest/v1/person")
            .request()
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
            .header("Nav-Personident", fnr)
            .header("Authorization", "Bearer $accessToken")
    }

    private fun hentKontaktinformasjon(request: Invocation.Builder): DigitalKontaktinformasjon {
        request.get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                if (CLIENT_ERROR == response.statusInfo.family) {
                    logger.warn("Fikk 4xx-feil i kall mot digdir-krr-proxy. Returnerer tomt objekt. ${response.status}: $responseBody")
                    return DigitalKontaktinformasjon(aktiv = false)
                }
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return JsonDeserialize.objectMapper.readValue(responseBody)
        }
    }
}
