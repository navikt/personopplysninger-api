package no.nav.personopplysninger.integration.endreopplysninger

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class SlettTelefonnummerIT : IntegrationTest() {

    val SLETT_TELEFONNUMMER_PATH = "/slettTelefonnummer"

    private val LANDKODE = "+47"
    private val NUMMER = "55553334"
    private val PRIORITET = 1

    @Test
    fun slettTelefonnummer200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = post(client,
            SLETT_TELEFONNUMMER_PATH,
            Telefonnummer(landskode = LANDKODE, nummer = NUMMER, prioritet = PRIORITET)
        )

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotPdlMottakSkalGi500() =
        integrationTest(setupMockedClient(pdlMottakStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client,
                SLETT_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = LANDKODE, nummer = NUMMER, prioritet = PRIORITET)
            )

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotPdlSkalGi500() =
        integrationTest(setupMockedClient(pdlStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = post(client,
                SLETT_TELEFONNUMMER_PATH,
                Telefonnummer(landskode = LANDKODE, nummer = NUMMER, prioritet = PRIORITET)
            )

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}