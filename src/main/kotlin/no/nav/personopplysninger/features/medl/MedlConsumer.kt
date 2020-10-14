package no.nav.personopplysninger.features.medl

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.*
import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class MedlConsumer constructor(
        private val client: Client,
        private val endpoint: URI,
        private val stsConsumer: STSConsumer
)
{
    fun hentMedlemskap(fnr: String): List<Medlemskapsunntak> {
        try {
            val response = getBuilder(fnr).get()
            if (!SUCCESSFUL.equals(response.statusInfo.family)) {
                val msg = "Forsøkte å konsumere REST-tjenesten medl. endpoint=[$endpoint], HTTP response status=[${response.status}]. "
                throw ConsumerException(msg.plus(response.unmarshalBody()))
            }
            return response.unmarshalList()
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten medl. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun getBuilder(fnr: String): Invocation.Builder {
        return client.target(endpoint)
                .path("v1/person/innsyn")
                .request()
                .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                .header(HEADER_NAV_CONSUMER_TOKEN, "Bearer $systemToken")
                .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                .header(HEADER_NAV_PERSONIDENT_KEY, fnr)
    }

    private val systemToken: String get() = stsConsumer.token.access_token
}