package no.nav.personopplysninger.endreopplysninger

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class SlettKontaktadresseIT : IntegrationTest() {

    val SLETT_KONTAKTADRESSE_PATH = "/slettKontaktadresse"

    @Test
    fun slettKontaktadresse200() =
        integrationTest(setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.SLETT_KONTAKTADRESSE)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client, SLETT_KONTAKTADRESSE_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun feilMotPdlMottakSkalGi500() =
        integrationTest(
            setupMockedClient(
                pdlMottakResponseType = PdlMottakResponseType.SLETT_KONTAKTADRESSE,
                pdlMottakStatus = HttpStatusCode.InternalServerError
            )
        ) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client, SLETT_KONTAKTADRESSE_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotPdlSkalGi500() =
        integrationTest(
            setupMockedClient(
                pdlMottakResponseType = PdlMottakResponseType.SLETT_KONTAKTADRESSE,
                pdlStatus = HttpStatusCode.InternalServerError
            )
        ) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client, SLETT_KONTAKTADRESSE_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}