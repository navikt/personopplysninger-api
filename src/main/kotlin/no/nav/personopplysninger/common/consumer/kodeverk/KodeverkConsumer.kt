package no.nav.personopplysninger.common.consumer.kodeverk

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.personopplysninger.common.consumer.kodeverk.dto.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.common.consumer.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.common.util.consumerErrorMessage
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import java.util.*

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
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
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
