package no.nav.personopplysninger.config

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import io.ktor.client.HttpClient
import io.ktor.http.Url
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.nav.personopplysninger.consumer.digdirkrr.KontaktinfoConsumer
import no.nav.personopplysninger.consumer.inst2.InstitusjonConsumer
import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Kontonummer
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.norg2.Norg2Consumer
import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.consumer.pdlmottak.PdlMottakConsumer
import no.nav.personopplysninger.endreopplysninger.EndreKontonummerState
import no.nav.personopplysninger.endreopplysninger.EndreOpplysningerService
import no.nav.personopplysninger.endreopplysninger.kafka.HendelseProducer
import no.nav.personopplysninger.institusjon.InstitusjonService
import no.nav.personopplysninger.kontaktinformasjon.KontaktinformasjonService
import no.nav.personopplysninger.medl.MedlService
import no.nav.personopplysninger.personalia.PersonaliaService
import no.nav.personopplysninger.testutils.FNR
import no.nav.personopplysninger.testutils.STATE
import no.nav.personopplysninger.testutils.createAccessToken
import org.apache.kafka.clients.producer.MockProducer
import java.net.URI

class TestApplicationContext(httpClient: HttpClient) {

    val env = Environment(
        corsAllowedOrigins = "",
        corsAllowedSchemes = "https",
        kafkaBrokers = "",
        kafkaTruststorePath = "",
        kafkaKeystorePath = "",
        kafkaCredstorePassword = "",
        varselHendelseTopic = "",
        redirectUri = "",
        frontendUri = "",
        wellKnownUrl = "",
        clientId = "",
        clientJwk = "",
        encryptionKey = "ZHVtbXk=",
        inst2Url = "https://inst2",
        kodeverkUrl = "https://kodeverk",
        norg2Url = "https://norg2",
        digdirKrrProxyUrl = "https://digdir-krr-proxy",
        pdlMottakUrl = "https://pdl-mottak",
        pdlUrl = "https://pdl",
        medlUrl = "https://medl",
        kontoregisterUrl = "https://kontoregister",
        inst2TargetApp = "",
        digdirKrrProxyTargetApp = "",
        medlTargetApp = "",
        pdlTargetApp = "",
        pdlMottakTargetApp = "",
        kontoregisterTargetApp = "",
        kodeverkTargetApp = "",
    )

    val tokendingsService = DummyTokendingsService()
    val azureService = DummyAzureService()
    val hendelseProducer = HendelseProducer(MockProducer(), "")

    val idporten: IDPorten = mockIdporten()

    val institusjonConsumer = InstitusjonConsumer(httpClient, env, tokendingsService)
    val kontaktinfoConsumer = KontaktinfoConsumer(httpClient, env, tokendingsService)
    val kontoregisterConsumer = KontoregisterConsumer(httpClient, env, tokendingsService)
    val kodeverkConsumer = KodeverkConsumer(httpClient, env, azureService)
    val medlConsumer = MedlConsumer(httpClient, env, tokendingsService)
    val norg2Consumer = Norg2Consumer(httpClient, env)
    val pdlConsumer = PdlConsumer(GraphQLKtorClient(URI(env.pdlUrl).toURL(), httpClient), env, tokendingsService)
    val pdlMottakConsumer = PdlMottakConsumer(httpClient, env, tokendingsService)

    val endreOpplysningerService =
        EndreOpplysningerService(
            pdlConsumer,
            pdlMottakConsumer,
            kodeverkConsumer,
            kontoregisterConsumer,
            hendelseProducer
        )
    val institusjonService = InstitusjonService(institusjonConsumer)
    val medlService = MedlService(medlConsumer, kodeverkConsumer)
    val kontaktinformasjonService = KontaktinformasjonService(kontaktinfoConsumer, kodeverkConsumer)
    val personaliaService = PersonaliaService(kodeverkConsumer, norg2Consumer, kontoregisterConsumer, pdlConsumer)

    private fun mockIdporten(): IDPorten {
        val idportenMock: IDPorten = mockk()
        coEvery { idportenMock.encrypt(any()) } returns ""
        coEvery { idportenMock.decrypt(any()) } returns Json.encodeToString(endreKontonummerState())
        coEvery { idportenMock.authorizeUrl(any(), any(), any()) } returns Url("")
        coEvery { idportenMock.token(any(), any(), any()) } returns createAccessToken()
        coEvery { idportenMock.frontendUri } returns Url("")
        coEvery { idportenMock.secureCookie } returns false
        return idportenMock
    }

    private fun endreKontonummerState() = EndreKontonummerState(
        state = STATE,
        nonce = "",
        codeVerifier = "",
        kontonummer = kontonummer(),
        bruker = FNR,
        locale = "",
    )

    private fun kontonummer() = Kontonummer("kilde", null, FNR)
}