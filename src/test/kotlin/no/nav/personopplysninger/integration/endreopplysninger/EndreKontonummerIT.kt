package no.nav.personopplysninger.integration.endreopplysninger

import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Kontonummer
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.FNR
import no.nav.personopplysninger.testutils.STATE
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class EndreKontonummerIT : IntegrationTest() {

    @Test
    fun endreKontonummer200() = integrationTest(setupMockedClient()) {
        val client = httpClient()
        val response = post(client, ENDRE_KONTNUMMER_PATH, Kontonummer("kilde", null, FNR))

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun lagreKontonummer200() = integrationTest(setupMockedClient()) {
        val client = httpClient()
        val response = get(
            client,
            LAGRE_KONTNUMMER_PATH,
            cookie = Pair("endreKontonummerState", "dummy"),
            queryParams = mapOf("state" to STATE, "code" to "code"),
        )

        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun valideringsfeilMotKontoregisterSkalGi400() =
        integrationTest(setupMockedClient(kontoregisterStatus = HttpStatusCode.NotAcceptable)) {
            val client = httpClient()
            val response = get(
                client,
                LAGRE_KONTNUMMER_PATH,
                cookie = Pair("endreKontonummerState", "dummy"),
                queryParams = mapOf("state" to STATE, "code" to "code"),
            )
            assertEquals(HttpStatusCode.Found, response.status)

            val location = URLBuilder().takeFrom(response.headers["Location"]!!)
            assertEquals(location.parameters["status"], "400")
            assertEquals(location.parameters["error"], "validation")
        }

    @Test
    fun feilMotKontoregisterSkalGi500() =
        integrationTest(setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)) {
            val client = httpClient()
            val response = get(
                client,
                LAGRE_KONTNUMMER_PATH,
                cookie = Pair("endreKontonummerState", "dummy"),
                queryParams = mapOf("state" to STATE, "code" to "code"),
            )
            assertEquals(HttpStatusCode.Found, response.status)

            val location = URLBuilder().takeFrom(response.headers["Location"]!!)
            assertEquals(location.parameters["status"], "500")
            assertEquals(location.parameters["error"], "unexpected")
        }

    companion object {
        private const val ENDRE_KONTNUMMER_PATH = "/endreKontonummer"
        private const val LAGRE_KONTNUMMER_PATH = "/lagreKontonummer"
    }
}