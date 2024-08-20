package no.nav.personopplysninger.consumer.kodeverk

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.consumer.kodeverk.dto.KodeverkBetydningerResponse
import no.nav.personopplysninger.util.consumerErrorMessage
import no.nav.tms.token.support.azure.exchange.AzureService
import java.util.*

class KodeverkConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val azureService: AzureService,
) {
    suspend fun hentRetningsnumre(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("Retningsnumre", true)
    }

    suspend fun hentKommuner(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("Kommuner", false)
    }

    suspend fun hentLandKoder(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("Landkoder", false)
    }

    suspend fun hentPostnummer(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("Postnummer", true)
    }

    suspend fun hentStatsborgerskap(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("StatsborgerskapFreg", true)
    }

    suspend fun hentDekningMedl(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("DekningMedl", true)
    }

    suspend fun hentGrunnlagMedl(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("GrunnlagMedl", true)
    }

    suspend fun hentSpraak(): KodeverkBetydningerResponse {
        return fetchFromKodeverk("Språk", true)
    }

    private suspend fun fetchFromKodeverk(navn: String, eksluderUgyldige: Boolean): KodeverkBetydningerResponse {
        kodeverkEndpoint(navn).let { endpoint ->
            azureService.getAccessToken(environment.kodeverkTargetApp).let { accessToken ->
                client.get(kodeverkEndpoint(endpoint)) {
                    parameter("spraak", "nb")
                    parameter("ekskluderUgyldige", eksluderUgyldige)
                    header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                    header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                    header(HEADER_AUTHORIZATION, BEARER + accessToken)
                }
            }.let { response ->
                return if (response.status.isSuccess()) {
                    response.body<KodeverkBetydningerResponse>()
                } else {
                    throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
                }
            }
        }
    }

    private fun kodeverkEndpoint(navn: String): String {
        return environment.kodeverkUrl.plus("/api/v1/kodeverk/$navn/koder/betydninger")
    }
}
