package no.nav.personopplysninger.consumer.kontoregister

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.consumer.kontoregister.dto.HentAktivKonto
import no.nav.personopplysninger.consumer.kontoregister.dto.Konto
import no.nav.personopplysninger.consumer.kontoregister.dto.OppdaterKonto
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.MDC

private const val HENT_KONTO_PATH = "/kontoregister/api/navno/v1/hent-aktiv-konto"
private const val OPPDATER_KONTO_PATH = "/kontoregister/api/navno/v1/oppdater-konto"

class KontoregisterConsumer constructor(
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
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
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
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                setBody(request)
            }
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
