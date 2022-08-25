package no.nav.personopplysninger.endreopplysninger

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentLandIT : IntegrationTest() {

    val HENT_LAND_PATH = "/land"

    @Test
    fun hentLand200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = get(client, HENT_LAND_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotKodeverkSkalGi500() =
        integrationTest(setupMockedClient(kodeverkStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_LAND_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}