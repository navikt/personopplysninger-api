package no.nav.personopplysninger.integration.personalia

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.config.mocks.PdlResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class HentPersonaliaIT : IntegrationTest() {

    val HENT_PERSONALIA_PATH = "/personalia"

    @Test
    fun hentPersonalia200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = get(client, HENT_PERSONALIA_PATH)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun hentPersonaliaOppholdAnnetSted200() =
        integrationTest(setupMockedClient(pdlResponseType = PdlResponseType.OPPHOLD_ANNET_STED)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun hentPersonaliaFlereAdresser200() =
        integrationTest(setupMockedClient(pdlResponseType = PdlResponseType.FLERE_ADRESSER)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun feilMotNorgSkalGi200() =
        integrationTest(setupMockedClient(norg2Status = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun feilMotPdlSkalGi500() =
        integrationTest(setupMockedClient(pdlStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotKontoregisterSkalGi500() =
        integrationTest(setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }

    @Test
    fun feilMotKodeverkSkalGi500() =
        integrationTest(setupMockedClient(kodeverkStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { json() } }
            val response = get(client, HENT_PERSONALIA_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}