package no.nav.personopplysninger.integration.endreopplysninger

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class EndreTelefonnummerIT : IntegrationTest() {

    val ENDRE_TELEFONNUMMER_PATH = "/endreTelefonnummer"

    @Test
    fun endreTelefonnummer200() =
        integrationTest(setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.TELEFON)) {
            val client = createClient { install(ContentNegotiation) { json() } }
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
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(
                client,
                ENDRE_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = "dummy", nummer = "dummy", prioritet = 1)
            )

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}