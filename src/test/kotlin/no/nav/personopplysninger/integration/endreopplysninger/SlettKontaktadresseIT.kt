package no.nav.personopplysninger.integration.endreopplysninger

import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class SlettKontaktadresseIT : IntegrationTest() {

    @Test
    fun slettKontaktadresse200() =
        integrationTest(setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.SLETT_KONTAKTADRESSE)) {
            val client = httpClient()
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
            val client = httpClient()
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
            val client = httpClient()
            val response = post(client, SLETT_KONTAKTADRESSE_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    companion object {
        private const val SLETT_KONTAKTADRESSE_PATH = "/slettKontaktadresse"
    }
}