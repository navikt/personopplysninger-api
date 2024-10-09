package no.nav.personopplysninger.integration.endreopplysninger

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class SlettKontaktadresseIT : IntegrationTest() {

    @Test
    fun slettKontaktadresse200() = integrationTest(
        setupMockedClient(pdlMottakResponseType = PdlMottakResponseType.SLETT_KONTAKTADRESSE)
    ) {
        val response = post(SLETT_KONTAKTADRESSE_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/slett-kontaktadresse.json")
    }

    @Test
    fun feilMotPdlMottakSkalGi500() = integrationTest(
        setupMockedClient(
            pdlMottakResponseType = PdlMottakResponseType.SLETT_KONTAKTADRESSE,
            pdlMottakStatus = HttpStatusCode.InternalServerError
        )
    ) {
        val response = post(SLETT_KONTAKTADRESSE_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    @Test
    fun feilMotPdlSkalGi500() = integrationTest(
        setupMockedClient(
            pdlMottakResponseType = PdlMottakResponseType.SLETT_KONTAKTADRESSE,
            pdlStatus = HttpStatusCode.InternalServerError
        )
    ) {
        val response = post(SLETT_KONTAKTADRESSE_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val SLETT_KONTAKTADRESSE_PATH = "/slettKontaktadresse"
    }
}