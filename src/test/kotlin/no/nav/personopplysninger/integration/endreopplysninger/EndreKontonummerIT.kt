package no.nav.personopplysninger.integration.endreopplysninger

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Kontonummer
import no.nav.personopplysninger.integration.IntegrationTest
import no.nav.personopplysninger.testutils.FNR
import no.nav.personopplysninger.testutils.STATE
import no.nav.personopplysninger.testutils.readJsonFile
import kotlin.test.Test

class EndreKontonummerIT : IntegrationTest() {

    @Test
    fun endreKontonummer200() = integrationTest(setupMockedClient()) {
        val response = post(
            path = ENDRE_KONTNUMMER_PATH,
            body = Kontonummer(value = FNR)
        )

        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldEqualJson readJsonFile("/json/expected-response/endre-kontonummer.json")
    }

    @Test
    fun lagreKontonummer200() = integrationTest(setupMockedClient()) {
        val response = get(
            path = LAGRE_KONTNUMMER_PATH,
            cookie = endreKontonummerStateCookie,
            queryParams = mapOf(stateQueryParam, codeQueryParam),
        )

        assertSoftly {
            response.status shouldBe HttpStatusCode.Found
            response.location.parameters["status"].shouldBeNull()
            response.location.parameters["error"].shouldBeNull()
        }
    }

    @Test
    fun valideringsfeilMotKontoregisterSkalGi400() = integrationTest(
        setupMockedClient(kontoregisterStatus = HttpStatusCode.NotAcceptable)
    ) {
        val response = get(
            LAGRE_KONTNUMMER_PATH,
            cookie = endreKontonummerStateCookie,
            queryParams = mapOf(stateQueryParam, codeQueryParam),
        )

        assertSoftly {
            response.status shouldBe HttpStatusCode.Found
            response.location.parameters["status"] shouldBe "400"
            response.location.parameters["error"] shouldBe "validation"
        }
    }

    @Test
    fun feilMotKontoregisterSkalGi500() = integrationTest(
        setupMockedClient(kontoregisterStatus = HttpStatusCode.InternalServerError)
    ) {
        val response = get(
            LAGRE_KONTNUMMER_PATH,
            cookie = endreKontonummerStateCookie,
            queryParams = mapOf(stateQueryParam, codeQueryParam),
        )

        assertSoftly {
            response.status shouldBe HttpStatusCode.Found
            response.location.parameters["status"] shouldBe "500"
            response.location.parameters["error"] shouldBe "unexpected"
        }
    }

    companion object {
        private const val ENDRE_KONTNUMMER_PATH = "/endreKontonummer"
        private const val LAGRE_KONTNUMMER_PATH = "/lagreKontonummer"

        private val endreKontonummerStateCookie = "endreKontonummerState" to "dummy"
        private val stateQueryParam = "state" to STATE
        private val codeQueryParam = "code" to "code"

        private val HttpResponse.location get() = URLBuilder().takeFrom(headers["Location"]!!)
    }
}