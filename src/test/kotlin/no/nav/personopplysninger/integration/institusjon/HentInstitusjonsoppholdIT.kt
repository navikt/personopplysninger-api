package no.nav.personopplysninger.integration.institusjon

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class HentInstitusjonsoppholdIT : IntegrationTest() {

    @Test
    fun hentInstitusjonsopphold200() = integrationTest(setupMockedClient()) {
        val response = get(HENT_INSTITUSJONSOPPHOLD_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/institusjonsopphold.json")
    }

    @Test
    fun feilMotInst2SkalGi500() = integrationTest(
        setupMockedClient(inst2Status = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_INSTITUSJONSOPPHOLD_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val HENT_INSTITUSJONSOPPHOLD_PATH = "/institusjonsopphold"
    }
}