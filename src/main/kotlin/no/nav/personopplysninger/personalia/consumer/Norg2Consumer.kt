package no.nav.personopplysninger.personalia.consumer

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.common.util.consumerErrorMessage
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_TOKEN
import no.nav.personopplysninger.personalia.consumer.dto.Norg2Enhet
import no.nav.personopplysninger.personalia.consumer.dto.Norg2EnhetKontaktinfo
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

class Norg2Consumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {

    private var logger: Logger = LoggerFactory.getLogger(javaClass)

    suspend fun hentEnhet(token: String, geografisk: String): Norg2Enhet? {
        val accessToken = tokenDingsService.exchangeToken(token, environment.personopplysningerProxyTargetApp)
        val endpoint = environment.norg2Url.plus("/enhet/navkontor/$geografisk")

        val response: HttpResponse =
            client.get(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header(HEADER_NAV_CONSUMER_TOKEN, token)
            }
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            logger.warn("Feil oppstod ved henting av enhet, returnerer tomt objekt. Status=[${response.status}], melding=[${response.body<String>()}]")
            null
        }
    }

    suspend fun hentKontaktinfo(token: String, enhetsnr: String): Norg2EnhetKontaktinfo {
        val accessToken = tokenDingsService.exchangeToken(token, environment.personopplysningerProxyTargetApp)
        val endpoint = environment.norg2Url.plus("/enhet/$enhetsnr/kontaktinformasjon")

        val response: HttpResponse =
            client.get(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header(HEADER_NAV_CONSUMER_TOKEN, token)
                header("enhetsnr", enhetsnr)
            }
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
