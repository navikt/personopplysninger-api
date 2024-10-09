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

fun MockRequestHandleScope.mockKodeverk(request: HttpRequestData, status: HttpStatusCode): HttpResponseData {
    return if (status.isSuccess()) {
        respond(
            content = readKodeverkResponse(request.url.encodedPath),
            headers = contentTypeJsonHeader()
        )
    } else {
        respondError(status)
    }
}

private fun readKodeverkResponse(path: String) = when {
    path.contains("Kommuner") -> readJsonFile("/json/mocks/kodeverk-kommuner.json")
    path.contains("Landkoder") -> readJsonFile("/json/mocks/kodeverk-land.json")
    path.contains("StatsborgerskapFreg") -> readJsonFile("/json/mocks/kodeverk-statsborgerskap.json")
    path.contains("Valutaer") -> readJsonFile("/json/mocks/kodeverk-valutaer.json")
    path.contains("Postnummer") -> readJsonFile("/json/mocks/kodeverk-postnummer.json")
    path.contains("GrunnlagMedl") -> readJsonFile("/json/mocks/kodeverk-grunnlagmedl.json")
    path.contains("DekningMedl") -> readJsonFile("/json/mocks/kodeverk-dekningmedl.json")
    path.contains("Retningsnumre") -> readJsonFile("/json/mocks/kodeverk-retningsnumre.json")
    path.contains("Språk") -> readJsonFile("/json/mocks/kodeverk-spraak.json")
    else -> throw IllegalArgumentException("Fant ikke mock for path: $path")
}