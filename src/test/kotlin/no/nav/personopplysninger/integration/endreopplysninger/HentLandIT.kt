package no.nav.personopplysninger.integration.endreopplysninger

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class HentLandIT : IntegrationTest() {

    @Test
    fun hentLand200() = integrationTest(setupMockedClient()) {
        val response = get(HENT_LAND_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/hent-land.json")
    }

    @Test
    fun feilMotKontoregisterSkalGi500() = integrationTest(
        setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_LAND_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val HENT_LAND_PATH = "/land"
    }
}