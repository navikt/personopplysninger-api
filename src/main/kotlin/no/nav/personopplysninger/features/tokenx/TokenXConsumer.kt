package no.nav.personopplysninger.features.tokenx

import no.nav.personopplysninger.consumerutils.*
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import no.nav.personopplysninger.oppslag.sts.TokenDto
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Form
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

internal const val PARAMS_GRANT_TYPE = "grant_type"
internal const val GRANT_TYPE = "urn:ietf:params:oauth:grant-type:token-exchange"
internal const val PARAMS_SUBJECT_TOKEN_TYPE = "subject_token_type"
internal const val SUBJECT_TOKEN_TYPE = "urn:ietf:params:oauth:token-type:jwt"
internal const val PARAMS_SUBJECT_TOKEN = "subject_token"
internal const val PARAMS_AUDIENCE = "audience"
internal const val PARAMS_CLIENT_ASSERTION = "client_assertion"
internal const val PARAMS_CLIENT_ASSERTION_TYPE = "client_assertion_type"
internal const val CLIENT_ASSERTION_TYPE = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer"

// todo lagre audience etc i .yml, basert på miljø

class TokenXConsumer constructor(
    private val client: Client,
    private val endpoint: URI,
    private val stsConsumer: STSConsumer
) {
    fun exchangeToken(): AccessTokenResponse {
        // todo dobbeltsjekk sidde verdiene
        val form = Form()
            .param(PARAMS_CLIENT_ASSERTION_TYPE, CLIENT_ASSERTION_TYPE)
            .param(PARAMS_CLIENT_ASSERTION, systemToken.toString())
            .param(PARAMS_GRANT_TYPE, GRANT_TYPE)
            .param(PARAMS_SUBJECT_TOKEN_TYPE, SUBJECT_TOKEN_TYPE)
            .param(PARAMS_SUBJECT_TOKEN, systemToken.access_token)
            .param(PARAMS_AUDIENCE, "dev-gcp:medlemskap:medlemskap-oppslag")

        try {
            val response = postBuilder().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
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

    private fun postBuilder(): Invocation.Builder {
        return client.target(endpoint)
            .path("/token")
            .request()
    }

    private val systemToken: TokenDto get() = stsConsumer.token
//    private val systemToken: TokenDto get() = stsConsumer.token.access_token
}

