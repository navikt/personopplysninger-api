package no.nav.personopplysninger.endreopplysninger

import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentLandIT : IntegrationTest() {

    val HENT_LAND_PATH = "/land"

    @Test
    fun hentLand200() = integrationTest(setupMockedClient()) {
        val client = httpClient()
        val response = get(client, HENT_LAND_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotKontoregisterSkalGi500() =
        integrationTest(setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)) {
            val client = httpClient()
            val response = get(client, HENT_LAND_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}