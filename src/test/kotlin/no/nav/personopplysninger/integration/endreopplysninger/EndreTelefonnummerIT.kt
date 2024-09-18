package no.nav.personopplysninger.integration.endreopplysninger

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class EndreTelefonnummerIT : IntegrationTest() {

    @Test
    fun endreTelefonnummer200() = integrationTest(
        setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.TELEFON)
    ) {
        val response = post(ENDRE_TELEFONNUMMER_PATH, dummyTelefonnummer)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/endre-telefonnummer.json")
    }

    @Test
    fun feilMotPdlMottakSkalGi500() = integrationTest(
        setupMockedClient(
            pdlMottakResponseType = PdlMottakResponseType.TELEFON,
            pdlMottakStatus = HttpStatusCode.InternalServerError
        )
    ) {
        val response = post(ENDRE_TELEFONNUMMER_PATH, dummyTelefonnummer)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val ENDRE_TELEFONNUMMER_PATH = "/endreTelefonnummer"
        private val dummyTelefonnummer = Telefonnummer(landskode = "dummy", nummer = "dummy")
    }
}