package no.nav.personopplysninger.integration.auth

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import no.nav.personopplysninger.config.LocalDateAdapter
import no.nav.personopplysninger.config.LocalDateTimeAdapter
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test

class HentNavnIT : IntegrationTest() {

    val HENT_NAVN_PATH = "/name"

    @Test
    fun hentNavn200() = integrationTest(setupMockedClient()) {
        val client = createClient { install(ContentNegotiation) { gson{
            registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        } } }
        val response = get(client, HENT_NAVN_PATH)

        //assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun feilMotPdlSkalGi500() =
        integrationTest(setupMockedClient(pdlStatus = HttpStatusCode.InternalServerError)) {
            val client = createClient { install(ContentNegotiation) { gson{
                registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
                registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            } } }

            val response = get(client, HENT_NAVN_PATH)

            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
}