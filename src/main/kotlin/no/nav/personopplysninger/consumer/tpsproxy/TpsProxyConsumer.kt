package no.nav.personopplysninger.consumer.tpsproxy

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.consumer.*
import no.nav.personopplysninger.consumer.JsonDeserialize.objectMapper
import no.nav.personopplysninger.consumer.tokendings.TokenDingsService
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.personopplysninger.util.getToken
import no.nav.tps.person.Personinfo
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class TpsProxyConsumer(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService,
    private val targetApp: String?
) {

    fun hentPersonInfo(fnr: String): Personinfo {
        val request = buildRequest(fnr)
        return hentPersoninfo(request)
    }

    private fun buildRequest(fnr: String): Invocation.Builder {
        val selvbetjeningToken = getToken()
        val accessToken = tokenDingsService.exchangeToken(selvbetjeningToken, targetApp).accessToken
        return client.target(endpoint)
            .path("person")
            .request()
            .header(HEADER_AUTHORIZATION, BEARER + accessToken)
            .header(HEADER_NAV_CONSUMER_TOKEN, selvbetjeningToken)
            .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
            .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            .header(HEADER_NAV_PERSONIDENT, fnr)
    }

    private fun hentPersoninfo(request: Invocation.Builder): Personinfo {
        request.get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return objectMapper.readValue(responseBody)
        }
    }
}
