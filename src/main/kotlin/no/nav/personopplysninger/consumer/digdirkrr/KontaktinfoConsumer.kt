package no.nav.personopplysninger.consumer.digdirkrr

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.config.HEADER_NAV_PERSONIDENT
import no.nav.personopplysninger.consumer.digdirkrr.dto.DigitalKontaktinformasjon
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import java.util.*

class KontaktinfoConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {

    suspend fun hentKontaktinformasjon(token: String, fnr: String): DigitalKontaktinformasjon {
        val accessToken = tokenDingsService.exchangeToken(token, environment.digdirKrrProxyTargetApp)
        val endpoint = environment.digdirKrrProxyUrl.plus("/rest/v1/person")

        val response: HttpResponse =
            client.get(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header(HEADER_NAV_PERSONIDENT, fnr)
            }
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
