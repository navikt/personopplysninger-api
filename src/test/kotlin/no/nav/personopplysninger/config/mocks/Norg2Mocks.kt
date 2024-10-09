package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import no.nav.personopplysninger.testutils.contentTypeJsonHeader
import no.nav.personopplysninger.testutils.readJsonFile

fun MockRequestHandleScope.mockNorg2(request: HttpRequestData, status: HttpStatusCode): HttpResponseData {
    return if (status.isSuccess()) {
        respond(
            content = readNorg2Response(request.url.encodedPath),
            headers = contentTypeJsonHeader()
        )
    } else {
        respondError(status)
    }
}

private fun readNorg2Response(path: String) = when {
    path.contains("navkontor") -> readJsonFile("/json/mocks/norg2-navkontor.json")
    path.contains("kontaktinformasjon") -> readJsonFile("/json/mocks/norg2-kontaktinformasjon.json")
    else -> throw IllegalArgumentException("Fant ikke mock for path: $path")
}