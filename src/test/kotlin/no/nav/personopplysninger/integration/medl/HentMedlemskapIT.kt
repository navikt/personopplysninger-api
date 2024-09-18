package no.nav.personopplysninger.integration.medl

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class HentMedlemskapIT : IntegrationTest() {

    @Test
    fun hentMedlemskap200() = integrationTest(setupMockedClient()) {
        val response = get(HENT_MEDLEMSKAP_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/medlemskap.json")
    }

    @Test
    fun feilMotMedlSkalGi500() = integrationTest(
        setupMockedClient(medlStatus = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_MEDLEMSKAP_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    @Test
    fun feilMotKodeverkSkalGi500() = integrationTest(
        setupMockedClient(kodeverkStatus = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_MEDLEMSKAP_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val HENT_MEDLEMSKAP_PATH = "/medl"
    }
}