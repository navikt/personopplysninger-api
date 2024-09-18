package no.nav.personopplysninger.integration.personalia

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import no.nav.personopplysninger.config.mocks.PdlResponseType
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class HentPersonaliaIT : IntegrationTest() {

    @Test
    fun hentPersonalia200() = integrationTest(setupMockedClient()) {
        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/personalia.json")
    }

    @Test
    fun hentPersonaliaOppholdAnnetSted200() = integrationTest(
        setupMockedClient(pdlResponseType = PdlResponseType.OPPHOLD_ANNET_STED)
    ) {
        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/personalia-opphold-annet-sted.json")
    }

    @Test
    fun hentPersonaliaFlereAdresser200() = integrationTest(
        setupMockedClient(pdlResponseType = PdlResponseType.FLERE_ADRESSER)
    ) {
        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/personalia-flere-adresser.json")
    }

    @Test
    fun feilMotNorgSkalGi200() = integrationTest(
        setupMockedClient(norg2Status = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/personalia-with-norg-error.json")
    }

    @Test
    fun feilMotPdlSkalGi500() = integrationTest(
        setupMockedClient(pdlStatus = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    @Test
    fun feilMotKontoregisterSkalGi200() = integrationTest(
        setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)
    ) {

        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/personalia-with-kontoregister-error.json")
    }

    @Test
    fun timeoutMotKontoregisterSkalGi200() = integrationTest(
        setupMockedClient(kontoregisterDelay = 2000)
    ) {
        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/personalia-with-kontoregister-error.json")
    }

    @Test
    fun feilMotKodeverkSkalGi500() = integrationTest(
        setupMockedClient(kodeverkStatus = HttpStatusCode.InternalServerError)
    ) {
        val response = get(HENT_PERSONALIA_PATH)

        response.status shouldBe HttpStatusCode.InternalServerError
    }

    companion object {
        private const val HENT_PERSONALIA_PATH = "/personalia"
    }
}