package no.nav.personopplysninger.institusjon

import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.IntegrationTest
import no.nav.personopplysninger.config.setupMockedClient
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentInstitusjonsoppholdIT : IntegrationTest() {

    val HENT_INSTITUSJONSOPPHOLD_PATH = "/institusjonsopphold"

    @Test
    fun hentInstitusjonsopphold200() = integrationTest(setupMockedClient()) {
        val client = httpClient()
        val response = get(client, HENT_INSTITUSJONSOPPHOLD_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotInst2SkalGi500() =
        integrationTest(setupMockedClient(inst2Status = HttpStatusCode.InternalServerError)) {
            val client = httpClient()

            val response = get(client, HENT_INSTITUSJONSOPPHOLD_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}