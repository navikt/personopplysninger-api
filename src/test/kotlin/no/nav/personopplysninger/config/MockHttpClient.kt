package no.nav.personopplysninger.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
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

private const val INST2 = "inst2"
private const val KODEVERK = "kodeverk"
private const val DIGDIR_KRR_PROXY = "digdir-krr-proxy"
private const val KONTOREGISTER = "kontoregister"
private const val MEDL = "medl"
private const val NORG2 = "norg2"
private const val PDL = "pdl"
private const val PDL_MOTTAK = "pdl-mottak"

fun setupMockedClient(
    inst2Status: HttpStatusCode = HttpStatusCode.OK,
    kodeverkStatus: HttpStatusCode = HttpStatusCode.OK,
    digdirKrrProxyStatus: HttpStatusCode = HttpStatusCode.OK,
    kontoregisterStatus: HttpStatusCode = HttpStatusCode.OK,
    kontoregisterDelay: Long = 0L,
    medlStatus: HttpStatusCode = HttpStatusCode.OK,
    norg2Status: HttpStatusCode = HttpStatusCode.OK,
    pdlStatus: HttpStatusCode = HttpStatusCode.OK,
    pdlResponseType: PdlResponseType = PdlResponseType.STANDARD,
    pdlMottakStatus: HttpStatusCode = HttpStatusCode.OK,
    pdlMottakResponseType: PdlMottakResponseType = PdlMottakResponseType.TELEFON,
): HttpClient {
    return runBlocking {
        HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    when (request.url.host) {
                        INST2 -> mockInst2(inst2Status)
                        KODEVERK -> mockKodeverk(request, kodeverkStatus)
                        DIGDIR_KRR_PROXY -> mockDigdirKrrProxy(digdirKrrProxyStatus)
                        KONTOREGISTER -> mockKontoregister(request, kontoregisterStatus, kontoregisterDelay)
                        MEDL -> mockMedl(medlStatus)
                        NORG2 -> mockNorg2(request, norg2Status)
                        PDL -> mockPdl(pdlStatus, pdlResponseType)
                        PDL_MOTTAK -> mockPdlMottak(request, pdlMottakStatus, pdlMottakResponseType)
                        else -> respondError(HttpStatusCode.NotFound)
                    }
                }

            }
            install(ContentNegotiation) {
                json(jsonConfig())
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 100L
            }
        }
    }
}