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


fun MockRequestHandleScope.mockKodeverk(request: HttpRequestData, status: HttpStatusCode) =
    if (status.isSuccess()) {
        respond(
            readKodeverkResponse(request.url.encodedPath),
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    } else {
        respondError(HttpStatusCode.InternalServerError)
    }

private fun readKodeverkResponse(path: String): String {
    return if (path.contains("Kommuner")) {
        readFile("kodeverk-kommuner.json")
    } else if (path.contains("Landkoder")) {
        readFile("kodeverk-land.json")
    } else if (path.contains("StatsborgerskapFreg")) {
        readFile("kodeverk-statsborgerskap.json")
    } else if (path.contains("Valutaer")) {
        readFile("kodeverk-valutaer.json")
    } else if (path.contains("Postnummer")) {
        readFile("kodeverk-postnummer.json")
    } else if (path.contains("GrunnlagMedl")) {
        readFile("kodeverk-grunnlagmedl.json")
    } else if (path.contains("DekningMedl")) {
        readFile("kodeverk-dekningmedl.json")
    } else if (path.contains("Retningsnumre")) {
        readFile("kodeverk-retningsnumre.json")
    } else if (path.contains("Spr%C3%A5k")) {
        readFile("kodeverk-spraak.json")
    } else {
        throw RuntimeException("Fant ikke mock for path")
    }
}