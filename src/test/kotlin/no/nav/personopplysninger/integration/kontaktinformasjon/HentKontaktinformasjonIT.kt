package no.nav.personopplysninger.integration.kontaktinformasjon

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class HentKontaktinformasjonIT : IntegrationTest() {

    @Test
    fun hentKontaktinformasjon200() = integrationTest(setupMockedClient()) {
        val response = get(HENT_KONTAKTINFORMASJON_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/kontaktinformasjon.json")
    }

    @Test
    fun serverfeilMotDigdirSkalGi500() = integrationTest(
        setupMockedClient(digdirKrrProxyStatus = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_KONTAKTINFORMASJON_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val HENT_KONTAKTINFORMASJON_PATH = "/kontaktinformasjon"
    }
}