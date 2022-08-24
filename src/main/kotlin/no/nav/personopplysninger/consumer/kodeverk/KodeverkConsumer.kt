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

class KodeverkConsumer(
    private val client: HttpClient,
    private val environment: Environment,
) {
    suspend fun fetchFromKodeverk(navn: String, eksluderUgyldige: Boolean): Kodeverk {
        val endpoint = environment.kodeverkUrl.plus("/api/v1/kodeverk/$navn/koder/betydninger")

        val response: HttpResponse =
            client.get(endpoint) {
                parameter("spraak", "nb")
                parameter("ekskluderUgyldige", eksluderUgyldige)
                header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            }
        return if (response.status.isSuccess()) {
            response.body<GetKodeverkKoderBetydningerResponse>()
                .let { Kodeverk.fromKoderBetydningerResponse(navn, it) }
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
