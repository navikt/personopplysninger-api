package no.nav.personopplysninger.features.personalia.pdl

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.CONSUMER_ID
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPersonInfo
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlResponse
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType

class PdlConsumer(private val client: Client, private val endpoint: URI, private val stsConsumer: STSConsumer) {

    // Defer to getTelefonInfo until more information is fetched from PDL
    fun getPersonInfo(ident: String): PdlPersonInfo {
        return getTelefonInfo(ident)
    }

    fun getTelefonInfo(ident: String): PdlPersonInfo {
        return postQuery(createTelefonRequest(ident)).data.hentPerson
    }


    private fun postQuery(request: PDLRequest): PdlResponse {
        return buildRequest()
                .post(Entity.entity(request, MediaType.APPLICATION_JSON))
                .unmarshalBody()
    }

    private fun buildRequest(): Invocation.Builder {
        return client.target(endpoint)
                .path("/graphql")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Consumer-Token", "Bearer $systemToken")
    }

    private val systemToken: String get() = stsConsumer.token.access_token
}