package no.nav.personopplysninger.endreopplysninger

import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class SlettTelefonnummerIT : IntegrationTest() {

    val SLETT_TELEFONNUMMER_PATH = "/slettTelefonnummer"

    private val LANDKODE = "+47"
    private val NUMMER = "55553334"
    private val PRIORITET = 1

    @Test
    fun slettTelefonnummer200() =
        integrationTest(setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.TELEFON)) {
            val client = httpClient()
            val response = post(
                client,
                SLETT_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = LANDKODE, nummer = NUMMER, prioritet = PRIORITET)
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
                SLETT_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = LANDKODE, nummer = NUMMER, prioritet = PRIORITET)
            )

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotPdlSkalGi500() =
        integrationTest(
            setupMockedClient(
                pdlMottakResponseType = PdlMottakResponseType.TELEFON,
                pdlStatus = HttpStatusCode.InternalServerError
            )
        ) {
            val client = httpClient()
            val response = post(
                client,
                SLETT_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = LANDKODE, nummer = NUMMER, prioritet = PRIORITET)
            )

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}