package no.nav.personopplysninger.endreopplysninger

import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class EndreTelefonnummerIT : IntegrationTest() {

    @Test
    fun endreTelefonnummer200() =
        integrationTest(setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.TELEFON)) {
            val client = httpClient()
            val response = post(
                client,
                ENDRE_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = "dummy", nummer = "dummy", prioritet = 1)
            )

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun feilMotPdlMottakSkalGi500() =
        integrationTest(
            setupMockedClient(
                pdlMottakResponseType = PdlMottakResponseType.TELEFON,
                pdlMottakStatus = HttpStatusCode.InternalServerError
            )
        ) {
            val client = httpClient()
            val response = post(
                client,
                ENDRE_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = "dummy", nummer = "dummy", prioritet = 1)
            )

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    companion object {
        private const val ENDRE_TELEFONNUMMER_PATH = "/endreTelefonnummer"
    }
}