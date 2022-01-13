package no.nav.personopplysninger.features.institusjon

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.features.institusjon.domain.InnsynInstitusjonsopphold
import no.nav.personopplysninger.util.*
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class InstitusjonConsumer constructor(private val client: Client, private val endpoint: URI) {

    fun getInstitusjonsopphold(fnr: String): List<InnsynInstitusjonsopphold> {
        buildRequest(fnr).get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return JsonDeserialize.objectMapper.readValue(responseBody)
        }
    }

    private fun buildRequest(fnr: String): Invocation.Builder {
        return client.target(endpoint)
            .path("v1/person/innsyn")
            .request()
            .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
            .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            .header(HEADER_NAV_PERSONIDENT_KEY, fnr)
    }
}
