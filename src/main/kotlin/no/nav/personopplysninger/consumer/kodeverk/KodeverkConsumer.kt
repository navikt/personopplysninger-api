package no.nav.personopplysninger.consumer.kodeverk

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.consumer.kodeverk.dto.KodeverkBetydningerResponse
import no.nav.personopplysninger.util.consumerErrorMessage
import java.util.*

class KodeverkConsumer(
    private val client: HttpClient,
    private val environment: Environment,
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
        val endpoint = environment.kodeverkUrl.plus("/api/v1/kodeverk/$navn/koder/betydninger")

        val response: HttpResponse =
            client.get(endpoint) {
                parameter("spraak", "nb")
                parameter("ekskluderUgyldige", eksluderUgyldige)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            }
        return if (response.status.isSuccess()) {
            response.body<KodeverkBetydningerResponse>()
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
