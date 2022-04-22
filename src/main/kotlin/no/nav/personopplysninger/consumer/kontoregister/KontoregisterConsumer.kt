package no.nav.personopplysninger.consumer.kontoregister

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.consumer.CONSUMER_ID
import no.nav.personopplysninger.consumer.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.consumer.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.consumer.JsonDeserialize.objectMapper
import no.nav.personopplysninger.consumer.kontoregister.domain.HentAktivKonto
import no.nav.personopplysninger.consumer.kontoregister.domain.Konto
import no.nav.personopplysninger.consumer.kontoregister.domain.OppdaterKonto
import no.nav.personopplysninger.consumer.tokendings.TokenDingsService
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.personopplysninger.util.getToken
import org.eclipse.jetty.http.HttpHeader
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

private const val HENT_KONTO_PATH = "kontoregister/api/navno/v1/hent-aktiv-konto"
private const val OPPDATER_KONTO_PATH = "kontoregister/api/navno/v1/oppdater-konto"

class KontoregisterConsumer constructor(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService,
    private val targetApp: String?
) {
    fun hentAktivKonto(fnr: String): Konto? {
        val tokendingsToken = tokenDingsService.exchangeToken(getToken(), targetApp)
        val request = HentAktivKonto(kontohaver = fnr)
        getBuilder(HENT_KONTO_PATH, tokendingsToken.accessToken).post(
            Entity.entity(
                request,
                MediaType.APPLICATION_JSON_TYPE
            )
        )
            .use { response ->
                val responseBody = response.readEntity(String::class.java)
                if (SUCCESSFUL != response.statusInfo.family) {
                    if (HttpStatus.NOT_FOUND_404 == response.status) {
                        return null
                    }
                    throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
                }
                return objectMapper.readValue(responseBody)
            }
    }

    fun endreKontonummer(request: OppdaterKonto) {
        val tokendingsToken = tokenDingsService.exchangeToken(getToken(), targetApp)
        getBuilder(OPPDATER_KONTO_PATH, tokendingsToken.accessToken).post(
            Entity.entity(
                request,
                MediaType.APPLICATION_JSON_TYPE
            )
        )
            .use { response ->
                if (SUCCESSFUL != response.statusInfo.family) {
                    throw ConsumerException(consumerErrorMessage(endpoint, response.status))
                }
            }
    }

    private fun getBuilder(path: String, accessToken: String): Invocation.Builder {
        return client.target(endpoint)
            .path(path)
            .request()
            .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
            .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            .header(HttpHeader.ACCEPT.asString(), MediaType.APPLICATION_JSON)
            .header(HttpHeader.AUTHORIZATION.asString(), "Bearer $accessToken")
    }
}
