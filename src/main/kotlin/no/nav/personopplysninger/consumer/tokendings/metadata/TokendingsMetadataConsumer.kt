package no.nav.personopplysninger.consumer.tokendings.metadata

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.personopplysninger.consumer.JsonDeserialize
import no.nav.personopplysninger.consumer.tokendings.domain.TokendingsMetaConfiguration
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.util.consumerErrorMessage
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response


class TokendingsMetadataConsumer constructor(
    private val client: Client,
    private val endpoint: URI
) {

    fun hentMetadata(): TokendingsMetaConfiguration {
        getBuilder().get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (Response.Status.Family.SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return JsonDeserialize.objectMapper.readValue(responseBody)
        }
    }

    private fun getBuilder(): Invocation.Builder {
        return client.target(endpoint)
            .request()
    }
}
