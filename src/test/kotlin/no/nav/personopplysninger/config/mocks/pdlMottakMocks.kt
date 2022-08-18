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


enum class PdlMottakResponseType {
    TELEFON, SLETT_KONTAKTADRESSE
}

fun MockRequestHandleScope.mockPdlMottak(
    request: HttpRequestData,
    status: HttpStatusCode,
    responseType: PdlMottakResponseType
) =
    if (status.isSuccess()) {
        if (request.url.encodedPath.contains("endringer")) {
            respond(
                "Suksess",
                headers = headersOf(HttpHeaders.Location, "location"),
            )
        } else if (request.url.encodedPath.contains("location")) {
            respond(
                readPdlMottakResponse(responseType),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        } else {
            throw RuntimeException("Fant ikke mock for path")
        }
    } else {
        respondError(status)
    }

private fun readPdlMottakResponse(responseType: PdlMottakResponseType): String {
    return when (responseType) {
        PdlMottakResponseType.TELEFON -> {
            readFile("endring-telefonnummer.json")
        }
        PdlMottakResponseType.SLETT_KONTAKTADRESSE -> {
            readFile("endring-kontaktadresse.json")
        }
    }
}