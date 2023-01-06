package no.nav.personopplysninger.common.consumer.kontoregister

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Landkode
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.ValidationError
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Valutakode
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.HentAktivKonto
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.OppdaterKonto
import no.nav.personopplysninger.common.consumer.kontoregister.exception.KontoregisterValidationException
import no.nav.personopplysninger.common.util.consumerErrorMessage
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

private const val HENT_KONTO_PATH = "/api/borger/v1/hent-aktiv-konto"
private const val OPPDATER_KONTO_PATH = "/api/borger/v1/oppdater-konto"
private const val HENT_LANDKODER_PATH = "/api/system/v1/hent-landkoder"
private const val HENT_VALUTAKODER_PATH = "/api/system/v1/hent-valutakoder"

class KontoregisterConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {

    private val logger: Logger = LoggerFactory.getLogger(KontoregisterConsumer::class.java)

    suspend fun hentAktivKonto(token: String, fnr: String): Konto? {
        val accessToken = tokenDingsService.exchangeToken(token, environment.kontoregisterTargetApp)
        val endpoint = environment.kontoregisterUrl.plus(HENT_KONTO_PATH)

        val request = HentAktivKonto(kontohaver = fnr)

        try {
            val response: HttpResponse =
                client.post(endpoint) {
                    header(HEADER_AUTHORIZATION, BEARER + accessToken)
                    header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                    header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                    contentType(ContentType.Application.Json)
                    setBody(request)
                    /* Setter kortere timeout midlertidig, da denne tjenesten er ny og har hatt litt nedetid.
                    Uheldig hvis det gjør at sluttbruker må vente opp til 9 sek på personalia.
                    Kontonummer-data bør muligens skilles ut i et eget endepunkt. */
                    timeout {
                        requestTimeoutMillis = 1000
                    }
                }
            return if (response.status.isSuccess()) {
                response.body()
            } else if (response.status == HttpStatusCode.NotFound) {
                null
            } else {
                logger.warn("Kall mot kontoregister feilet med status ${response.status}. Returnerer feilobjekt.")
                Konto(error = true)
            }
        } catch (e: Exception) {
            val feilmelding = when (e) {
                is SocketTimeoutException,
                is HttpRequestTimeoutException,
                is ConnectTimeoutException -> "Kall mot kontoregister timet ut. Returnerer feilobjekt."
                else -> "Ukjent feil ved kall mot kontoregister. Returnerer feilobjekt."
            }
            logger.warn(feilmelding, e)
            return Konto(error = true)
        }
    }

    suspend fun hentLandkoder(): List<Landkode> {
        val endpoint = environment.kontoregisterUrl.plus(HENT_LANDKODER_PATH)
        return client.get(endpoint).body()
    }

    suspend fun hentValutakoder(): List<Valutakode> {
        val endpoint = environment.kontoregisterUrl.plus(HENT_VALUTAKODER_PATH)
        return client.get(endpoint).body()
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
            if (response.status == HttpStatusCode.NotAcceptable) {
                val errorResponse: ValidationError = response.body()
                throw KontoregisterValidationException(errorResponse.feilmelding)
            }
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
