package no.nav.personopplysninger.consumer.kodeverk

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.consumer.kodeverk.dto.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.consumer.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.util.consumerErrorMessage
import org.slf4j.MDC

open class KodeverkConsumer constructor(
    private val client: HttpClient,
    private val environment: Environment,
) {
    open suspend fun hentRetningsnumre(): Kodeverk {
        return hentKodeverkBetydning("Retningsnumre", true)
    }

    open suspend fun hentKommuner(): Kodeverk {
        return hentKodeverkBetydning("Kommuner", false)
    }

    open suspend fun hentLandKoder(): Kodeverk {
        return hentKodeverkBetydning("Landkoder", true)
    }

    open suspend fun hentLandKoderISO2(): Kodeverk {
        return hentKodeverkBetydning("LandkoderISO2", true)
    }

    open suspend fun hentPostnummer(): Kodeverk {
        return hentKodeverkBetydning("Postnummer", true)
    }

    open suspend fun hentValuta(): Kodeverk {
        return hentKodeverkBetydning("Valutaer", true)
    }

    open suspend fun hentStatsborgerskap(): Kodeverk {
        return hentKodeverkBetydning("StatsborgerskapFreg", true)
    }

    open suspend fun hentDekningMedl(): Kodeverk {
        return hentKodeverkBetydning("DekningMedl", true)
    }

    open suspend fun hentGrunnlagMedl(): Kodeverk {
        return hentKodeverkBetydning("GrunnlagMedl", true)
    }

    open suspend fun hentSpraak(): Kodeverk {
        return hentKodeverkBetydning("Språk", true)
    }

    private suspend fun hentKodeverkBetydning(navn: String, eksluderUgyldige: Boolean): Kodeverk {
        val endpoint = environment.kodeverkUrl.plus("/api/v1/kodeverk/$navn/koder/betydninger")

        val response: HttpResponse =
            client.get(endpoint) {
                parameter("spraak", "nb")
                parameter("ekskluderUgyldige", eksluderUgyldige)
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            }
        return if (response.status.isSuccess()) {
            response.body<GetKodeverkKoderBetydningerResponse>().let { Kodeverk.fromKoderBetydningerResponse(navn, it) }
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
