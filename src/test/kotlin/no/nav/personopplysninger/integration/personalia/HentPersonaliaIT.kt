package no.nav.personopplysninger.integration.personalia

import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.mocks.PdlResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class HentPersonaliaIT : IntegrationTest() {

    @Test
    fun hentPersonalia200() = integrationTest(setupMockedClient()) {
        val client = httpClient()
        val response = get(client, HENT_PERSONALIA_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun hentPersonaliaOppholdAnnetSted200() =
        integrationTest(setupMockedClient(pdlResponseType = PdlResponseType.OPPHOLD_ANNET_STED)) {
            val client = httpClient()
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun hentPersonaliaFlereAdresser200() =
        integrationTest(setupMockedClient(pdlResponseType = PdlResponseType.FLERE_ADRESSER)) {
            val client = httpClient()
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun feilMotNorgSkalGi200() =
        integrationTest(setupMockedClient(norg2Status = HttpStatusCode.InternalServerError)) {
            val client = httpClient()
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun feilMotPdlSkalGi500() =
        integrationTest(setupMockedClient(pdlStatus = HttpStatusCode.InternalServerError)) {
            val client = httpClient()
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotKontoregisterSkalGi200() =
        integrationTest(setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)) {
            val client = httpClient()
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("\"kontoregisterStatus\": \"ERROR\""))
        }

    @Test
    fun timeoutMotKontoregisterSkalGi200() =
        integrationTest(setupMockedClient(kontoregisterDelay = 2000)) {
            val client = httpClient()
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("\"kontoregisterStatus\": \"ERROR\""))
        }

    @Test
    fun feilMotKodeverkSkalGi500() =
        integrationTest(setupMockedClient(kodeverkStatus = HttpStatusCode.InternalServerError)) {
            val client = httpClient()
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    companion object {
        private const val HENT_PERSONALIA_PATH = "/personalia"
    }
}