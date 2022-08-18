package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess
import no.nav.personopplysninger.testutils.TestFileReader.readFile


fun MockRequestHandleScope.mockNorg2(request: HttpRequestData, status: HttpStatusCode) =
    if (status.isSuccess()) {
        respond(
            readNorg2Response(request.url.encodedPath),
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    } else {
        respondError(HttpStatusCode.InternalServerError)
    }

private fun readNorg2Response(path: String): String {
    return if (path.contains("navkontor")) {
        readFile("norg2-navkontor.json")
    } else if (path.startsWith("kontaktinformasjon")) {
        readFile("norg2-kontaktinformasjon.json")
    } else {
        throw RuntimeException("Fant ikke mock for path")
    }
}