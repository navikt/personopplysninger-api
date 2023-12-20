package no.nav.personopplysninger.medl

import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentMedlemskapIT : IntegrationTest() {

    val HENT_MEDLEMSKAP_PATH = "/medl"

    @Test
    fun hentMedlemskap200() = integrationTest(setupMockedClient()) {
        val client = httpClient()
        val response = get(client, HENT_MEDLEMSKAP_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotMedlSkalGi500() =
        integrationTest(setupMockedClient(medlStatus = HttpStatusCode.InternalServerError)) {
            val client = httpClient()

            val response = get(client, HENT_MEDLEMSKAP_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotKodeverkSkalGi500() =
        integrationTest(setupMockedClient(kodeverkStatus = HttpStatusCode.InternalServerError)) {
            val client = httpClient()

            val response = get(client, HENT_MEDLEMSKAP_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}