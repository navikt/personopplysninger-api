package no.nav.personopplysninger.features.institusjon

import no.nav.log.MDCConstants
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.personopplysninger.features.institusjon.dto.InnsynInstitusjonsopphold
import no.nav.personopplysninger.features.personalia.exceptions.ConsumerException
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class InstitusjonConsumer constructor(
        private val client: Client,
        private val endpoint: URI
)
{
    fun getInstitusjonsopphold(fnr: String): List<InnsynInstitusjonsopphold> {
        try {
            val response = getBuilder(fnr).get()
            if (!SUCCESSFUL.equals(response.statusInfo.family)) {
                val msg = "Forsøkte å konsumere REST-tjenesten INST2. endpoint=[$endpoint], HTTP response status=[${response.status}]. "
                throw ConsumerException(msg.plus(ConsumerFactory.readEntity(String::class.java, response)))
            }
            return ConsumerFactory.readEntity(ArrayList<InnsynInstitusjonsopphold>()::class.java, response)
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten INST2. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun getBuilder(fnr: String): Invocation.Builder {
        return client.target(endpoint)
                .path("v1/person/innsyn")
                .request()
                .header(ConsumerFactory.HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                .header(ConsumerFactory.HEADER_NAV_CONSUMER_ID, ConsumerFactory.CONSUMER_ID)
                .header(ConsumerFactory.HEADER_NAV_PERSONIDENT_KEY, fnr)
    }
}