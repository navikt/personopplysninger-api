package no.nav.personopplysninger.integration.endreopplysninger

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class SlettKontaktadresseIT : IntegrationTest() {

    val SLETT_KONTAKTADRESSE_PATH = "/slettKontaktadresse"

    @Test
    fun slettKontaktadresse200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = post(client, SLETT_KONTAKTADRESSE_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotPdlMottakSkalGi500() =
        integrationTest(setupMockedClient(pdlMottakStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client, SLETT_KONTAKTADRESSE_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotPdlSkalGi500() =
        integrationTest(setupMockedClient(pdlStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client, SLETT_KONTAKTADRESSE_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}