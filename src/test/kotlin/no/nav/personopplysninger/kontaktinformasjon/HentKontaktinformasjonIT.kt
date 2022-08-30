package no.nav.personopplysninger.kontaktinformasjon

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentKontaktinformasjonIT : IntegrationTest() {

    val HENT_KONTAKTINFORMASJON_PATH = "/kontaktinformasjon"

    @Test
    fun hentKontaktinformasjon200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = get(client, HENT_KONTAKTINFORMASJON_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun serverfeilMotDigdirSkalGi500() =
        integrationTest(setupMockedClient(digdirKrrProxyStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }

            val response = get(client, HENT_KONTAKTINFORMASJON_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}