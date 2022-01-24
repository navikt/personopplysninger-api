package no.nav.personopplysninger.features.personalia

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.exception.consumerErrorMessage
import no.nav.personopplysninger.util.CONSUMER_ID
import no.nav.personopplysninger.util.JsonDeserialize.objectMapper
import no.nav.tps.person.Personinfo
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class PersonConsumer(private val client: Client, private val endpoint: URI) {

    fun hentPersonInfo(fnr: String): Personinfo {
        val request = buildRequest(fnr)
        return hentPersoninfo(request)
    }

    private fun buildRequest(fnr: String): Invocation.Builder {
        return client.target(endpoint)
            .path("person")
            .request()
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
            .header("Nav-Personident", fnr)
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
