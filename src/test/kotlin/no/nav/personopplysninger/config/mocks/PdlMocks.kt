package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import no.nav.personopplysninger.testutils.contentTypeJsonHeader
import no.nav.personopplysninger.testutils.readJsonFile

enum class PdlResponseType {
    STANDARD, FLERE_ADRESSER, OPPHOLD_ANNET_STED
}

fun MockRequestHandleScope.mockPdl(status: HttpStatusCode, responseType: PdlResponseType): HttpResponseData {
    return if (status.isSuccess()) {
        respond(
            content = readPdlResponse(responseType),
            headers = contentTypeJsonHeader()
        )
    } else {
        respondError(status)
    }
}

private fun readPdlResponse(responseType: PdlResponseType) = when (responseType) {
    PdlResponseType.STANDARD -> readJsonFile("/json/mocks/pdl.json")
    PdlResponseType.FLERE_ADRESSER -> readJsonFile("/json/mocks/pdl_flere_adresser.json")
    PdlResponseType.OPPHOLD_ANNET_STED -> readJsonFile("/json/mocks/pdl_opphold_annet_sted.json")
}