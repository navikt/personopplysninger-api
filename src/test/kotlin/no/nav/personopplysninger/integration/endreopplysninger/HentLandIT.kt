package no.nav.personopplysninger.integration.endreopplysninger

import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentLandIT : IntegrationTest() {

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

    companion object {
        private const val HENT_LAND_PATH = "/land"
    }
}