package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess
import no.nav.personopplysninger.testutils.contentTypeJsonHeader
import no.nav.personopplysninger.testutils.readJsonFile

enum class PdlMottakResponseType {
    TELEFON, SLETT_KONTAKTADRESSE
}

fun MockRequestHandleScope.mockPdlMottak(
    request: HttpRequestData,
    status: HttpStatusCode,
    responseType: PdlMottakResponseType
): HttpResponseData {
    return if (status.isSuccess()) {
        val (content, headers) = successResponseParams(request.url.encodedPath, responseType)
        respond(content = content, headers = headers)
    } else {
        respondError(status)
    }
}

private fun successResponseParams(
    path: String,
    responseType: PdlMottakResponseType
) = when {
    path.contains("endringer") -> "Suksess" to headersOf(HttpHeaders.Location, "/location")
    path.contains("location") -> readPdlMottakResponse(responseType) to contentTypeJsonHeader()
    else -> throw IllegalArgumentException("Fant ikke mock for path: $path")
}

private fun readPdlMottakResponse(responseType: PdlMottakResponseType) = when (responseType) {
    PdlMottakResponseType.TELEFON -> readJsonFile("/json/mocks/endring-telefonnummer.json")
    PdlMottakResponseType.SLETT_KONTAKTADRESSE -> readJsonFile("/json/mocks/endring-kontaktadresse.json")
}