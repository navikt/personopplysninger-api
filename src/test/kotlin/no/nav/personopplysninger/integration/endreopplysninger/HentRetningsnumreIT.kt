package no.nav.personopplysninger.integration.endreopplysninger

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentRetningsnumreIT : IntegrationTest() {

    val HENT_RETNINGSNUMRE_PATH = "/retningsnumre"

    @Test
    fun hentRetningsnumre200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = get(client, HENT_RETNINGSNUMRE_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotKodeverkSkalGi500() =
        integrationTest(setupMockedClient(kodeverkStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_RETNINGSNUMRE_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}