package no.nav.personopplysninger.endreopplysninger

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Kontonummer
import no.nav.personopplysninger.config.mocks.KONTONUMMER_VALIDERINGSFEIL
import no.nav.personopplysninger.config.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class EndreKontonummerIT : IntegrationTest() {

    val ENDRE_KONTNUMMER_PATH = "/endreKontonummer"

    @Test
    fun endreKontonummer200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = post(client, ENDRE_KONTNUMMER_PATH, Kontonummer("kilde", null, "12345678911"))

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun valideringsfeilMotKontoregisterSkalGi400() =
        integrationTest(setupMockedClient(kontoregisterStatus = HttpStatusCode.NotAcceptable)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client, ENDRE_KONTNUMMER_PATH, Kontonummer("kilde", null, "12345678911"))

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertEquals(KONTONUMMER_VALIDERINGSFEIL, response.bodyAsText())
        }

    @Test
    fun feilMotKontoregisterSkalGi500() =
        integrationTest(setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client, ENDRE_KONTNUMMER_PATH, Kontonummer("kilde", null, "12345678911"))

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}