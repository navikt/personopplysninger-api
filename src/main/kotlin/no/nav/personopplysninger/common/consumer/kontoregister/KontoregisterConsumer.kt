package no.nav.personopplysninger.common.consumer.kontoregister

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.HentAktivKonto
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.OppdaterKonto
import no.nav.personopplysninger.common.util.consumerErrorMessage
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import java.util.*

private const val HENT_KONTO_PATH = "/api/borger/v1/hent-aktiv-konto"
private const val OPPDATER_KONTO_PATH = "/api/borger/v1/oppdater-konto"

class KontoregisterConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {
    suspend fun hentAktivKonto(token: String, fnr: String): Konto? {
        val accessToken = tokenDingsService.exchangeToken(token, environment.kontoregisterTargetApp)
        val endpoint = environment.kontoregisterUrl.plus(HENT_KONTO_PATH)

        val request = HentAktivKonto(kontohaver = fnr)

        val response: HttpResponse =
            client.post(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        return if (response.status.isSuccess()) {
            response.body()
        } else if (response.status == HttpStatusCode.NotFound) {
            null
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }

    suspend fun endreKontonummer(token: String, request: OppdaterKonto) {
        val accessToken = tokenDingsService.exchangeToken(token, environment.kontoregisterTargetApp)
        val endpoint = environment.kontoregisterUrl.plus(OPPDATER_KONTO_PATH)

        val response: HttpResponse =
            client.post(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        if (!response.status.isSuccess()) {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
