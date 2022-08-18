package no.nav.personopplysninger.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import no.nav.personopplysninger.config.mocks.PdlMottakResponseType
import no.nav.personopplysninger.config.mocks.PdlResponseType
import no.nav.personopplysninger.config.mocks.mockDigdirKrrProxy
import no.nav.personopplysninger.config.mocks.mockInst2
import no.nav.personopplysninger.config.mocks.mockKodeverk
import no.nav.personopplysninger.config.mocks.mockKontoregister
import no.nav.personopplysninger.config.mocks.mockMedl
import no.nav.personopplysninger.config.mocks.mockNorg2
import no.nav.personopplysninger.config.mocks.mockPdl
import no.nav.personopplysninger.config.mocks.mockPdlMottak


fun setupMockedClient(
    inst2Status: HttpStatusCode = HttpStatusCode.OK,
    kodeverkStatus: HttpStatusCode = HttpStatusCode.OK,
    digdirKrrProxyStatus: HttpStatusCode = HttpStatusCode.OK,
    kontoregisterStatus: HttpStatusCode = HttpStatusCode.OK,
    medlStatus: HttpStatusCode = HttpStatusCode.OK,
    norg2Status: HttpStatusCode = HttpStatusCode.OK,
    pdlStatus: HttpStatusCode = HttpStatusCode.OK,
    pdlResponseType: PdlResponseType = PdlResponseType.STANDARD,
    pdlMottakStatus: HttpStatusCode = HttpStatusCode.OK,
    pdlMottakResponseType: PdlMottakResponseType = PdlMottakResponseType.TELEFON,
): HttpClient {
    val INST2 = "inst2"
    val KODEVERK = "kodeverk"
    val DIGDIR_KRR_PROXY = "digdir-krr-proxy"
    val KONTOREGISTER = "kontoregister"
    val MEDL = "medl"
    val NORG2 = "norg2"
    val PDL = "pdl"
    val PDL_MOTTAK = "pdl-mottak"

    return HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.host) {
                    INST2 -> {
                        mockInst2(inst2Status)
                    }
                    KODEVERK -> {
                        mockKodeverk(request, kodeverkStatus)
                    }
                    DIGDIR_KRR_PROXY -> {
                        mockDigdirKrrProxy(digdirKrrProxyStatus)
                    }
                    KONTOREGISTER -> {
                        mockKontoregister(kontoregisterStatus)
                    }
                    MEDL -> {
                        mockMedl(medlStatus)
                    }
                    NORG2 -> {
                        mockNorg2(request, norg2Status)
                    }
                    PDL -> {
                        mockPdl(pdlStatus, pdlResponseType)
                    }
                    PDL_MOTTAK -> {
                        mockPdlMottak(request, pdlMottakStatus, pdlMottakResponseType)
                    }
                    else -> {
                        respondError(HttpStatusCode.NotFound)
                    }
                }
            }

        }
        install(ContentNegotiation) {
            json(jsonConfig())
        }
        install(HttpTimeout)
        expectSuccess = false
    }
}