package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess
import kotlinx.serialization.encodeToString
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.ValidationError
import no.nav.personopplysninger.config.jsonConfig
import no.nav.personopplysninger.testutils.TestFileReader.readFile

const val KONTONUMMER_VALIDERINGSFEIL = "Valideringsfeil"

fun MockRequestHandleScope.mockKontoregister(status: HttpStatusCode) =
    if (status.isSuccess()) {
        respond(
            readFile("kontoregister.json"),
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    } else {
        if (HttpStatusCode.NotAcceptable == status) {
            respondError(
                content = jsonConfig().encodeToString(ValidationError(feilmelding = KONTONUMMER_VALIDERINGSFEIL)),
                status = HttpStatusCode.NotAcceptable,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        } else {
            respondError(status)
        }
    }