package no.nav.personopplysninger.features.tokendings

import no.nav.personopplysninger.consumerutils.ConsumerException
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.features.tokendings.domain.TokendingsToken
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Form
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class TokendingsConsumer constructor(
    private val client: Client,
    private val endpoint: URI
)   {

    fun exchangeToken(subjectToken: String, clientAssertion: String, audience: String): TokendingsToken {
        val form = Form()
            .param("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
            .param("client_assertion", clientAssertion)
            .param("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange")
            .param("subject_token_type", "urn:ietf:params:oauth:token-type:jwt")
            .param("subject_token", subjectToken)
            .param("audience", audience)

        try {
            val response = buildRequest().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
            if (Response.Status.Family.SUCCESSFUL != response.statusInfo.family) {
                val msg = "Forsøkte å konsumere REST-tjenesten tokendings. endpoint=[$endpoint], HTTP response status=[${response.status}]. "
                throw ConsumerException(msg.plus(response.unmarshalBody()))
            }

            return response.unmarshalBody()
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten tokendings. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun buildRequest(): Invocation.Builder {
        return client.target(endpoint)
            .request()
    }
}
