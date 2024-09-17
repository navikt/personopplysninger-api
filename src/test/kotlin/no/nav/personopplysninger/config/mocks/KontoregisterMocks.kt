package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import no.nav.personopplysninger.config.jsonConfig
import no.nav.personopplysninger.consumer.kontoregister.dto.response.ValidationError
import no.nav.personopplysninger.testutils.contentTypeJsonHeader
import no.nav.personopplysninger.testutils.readJsonFile

suspend fun MockRequestHandleScope.mockKontoregister(
    request: HttpRequestData,
    status: HttpStatusCode,
    delayMilliseconds: Long
): HttpResponseData {
    return if (status.isSuccess()) {
        if (delayMilliseconds > 0) delay(delayMilliseconds)
        respond(
            content = readKontoregisterResponse(request.url.encodedPath),
            headers = contentTypeJsonHeader()
        )
    } else if (status == HttpStatusCode.NotAcceptable) respondError(
        content = jsonConfig().encodeToString(ValidationError(feilmelding = "Valideringsfeil")),
        status = HttpStatusCode.NotAcceptable,
        headers = contentTypeJsonHeader()
    )
    else {
        respondError(status)
    }
}

private fun readKontoregisterResponse(path: String) = when {
    path.contains("hent-aktiv-konto") -> readJsonFile("/json/kontoregister.json")
    path.contains("oppdater-konto") -> readJsonFile("/json/kontoregister.json")
    path.contains("hent-landkoder") -> readJsonFile("/json/kontoregister-landkoder.json")
    path.contains("hent-valutakoder") -> readJsonFile("/json/kontoregister-valutakoder.json")
    else -> throw IllegalArgumentException("Fant ikke mock for path: $path")
}