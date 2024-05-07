package no.nav.personopplysninger.config.mocks

import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess
import no.nav.personopplysninger.testutils.TestFileReader.readFile

enum class PdlResponseType {
    STANDARD, FLERE_ADRESSER, OPPHOLD_ANNET_STED, IKKE_MYNDIG
}

fun MockRequestHandleScope.mockPdl(status: HttpStatusCode, responseType: PdlResponseType) =
    if (status.isSuccess()) {
        respond(
            readPdlResponse(responseType),
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    } else {
        respondError(status)
    }

private fun readPdlResponse(responseType: PdlResponseType): String {
    return when (responseType) {
        PdlResponseType.STANDARD -> {
            readFile("pdl.json")
        }
        PdlResponseType.FLERE_ADRESSER -> {
            readFile("pdl_flere_adresser.json")
        }
        PdlResponseType.OPPHOLD_ANNET_STED -> {
            readFile("pdl_opphold_annet_sted.json")
        }
        PdlResponseType.IKKE_MYNDIG -> {
            readFile("pdl_ikke_myndig.json")
        }
    }
}