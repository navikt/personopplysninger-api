package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import no.nav.personopplysninger.testutils.contentTypeJsonHeader
import no.nav.personopplysninger.testutils.readJsonFile

fun MockRequestHandleScope.mockMedl(status: HttpStatusCode): HttpResponseData {
    return if (status.isSuccess()) {
        respond(
            content = readJsonFile("/json/medl-response.json"),
            headers = contentTypeJsonHeader()
        )
    } else {
        respondError(status)
    }
}