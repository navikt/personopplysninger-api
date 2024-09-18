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

class SlettTelefonnummerIT : IntegrationTest() {

    @Test
    fun slettTelefonnummer200() = integrationTest(
        setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.TELEFON)
    ) {
        val response = post(SLETT_TELEFONNUMMER_PATH, dummyTelefonnummer)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/slett-telefonnummer.json")
    }

    @Test
    fun feilMotPdlMottakSkalGi500() = integrationTest(
        setupMockedClient(
            pdlMottakResponseType = PdlMottakResponseType.TELEFON,
            pdlMottakStatus = HttpStatusCode.InternalServerError
        )
    ) {
        val response = post(SLETT_TELEFONNUMMER_PATH, dummyTelefonnummer)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    @Test
    fun feilMotPdlSkalGi500() = integrationTest(
        setupMockedClient(
            pdlMottakResponseType = PdlMottakResponseType.TELEFON,
            pdlStatus = HttpStatusCode.InternalServerError
        )
    ) {
        val response = post(SLETT_TELEFONNUMMER_PATH, dummyTelefonnummer)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val SLETT_TELEFONNUMMER_PATH = "/slettTelefonnummer"
        private const val LANDKODE = "+47"
        private const val NUMMER = "55553334"
        private val dummyTelefonnummer = Telefonnummer(landskode = LANDKODE, nummer = NUMMER)
    }
}