package no.nav.personopplysninger.features.tokendings.metadata

import no.nav.personopplysninger.consumerutils.ConsumerException
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.features.tokendings.domain.TokendingsMetaConfiguration
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response


class TokendingsMetadataConsumer constructor(
    private val client: Client,
    private val endpoint: URI
) {

    fun hentMetadata(): TokendingsMetaConfiguration {
        try {
            val response = getBuilder().get()
            if (Response.Status.Family.SUCCESSFUL != response.statusInfo.family) {
                val msg = "Forsøkte å konsumere REST-tjenesten tokendings metadata. endpoint=[$endpoint], HTTP response status=[${response.status}]. "
                throw ConsumerException(msg.plus(response.unmarshalBody()))
            }

            return response.unmarshalBody()
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere REST-tjenesten tokendings metadata. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun getBuilder(): Invocation.Builder {
        return client.target(endpoint)
            .request()
    }
}
