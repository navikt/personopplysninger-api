package no.nav.personopplysninger.config

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.ktor.client.HttpClient
import io.ktor.http.Url
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.nav.personopplysninger.common.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.common.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.common.consumer.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.common.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.common.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.common.consumer.pdl.PdlService
import no.nav.personopplysninger.endreopplysninger.EndreOpplysningerService
import no.nav.personopplysninger.endreopplysninger.consumer.PdlMottakConsumer
import no.nav.personopplysninger.endreopplysninger.kafka.HendelseProducer
import no.nav.personopplysninger.institusjon.InstitusjonService
import no.nav.personopplysninger.institusjon.consumer.InstitusjonConsumer
import no.nav.personopplysninger.kontaktinformasjon.KontaktinformasjonService
import no.nav.personopplysninger.kontaktinformasjon.consumer.KontaktinfoConsumer
import no.nav.personopplysninger.medl.MedlService
import no.nav.personopplysninger.medl.consumer.MedlConsumer
import no.nav.personopplysninger.personalia.PersonaliaService
import no.nav.personopplysninger.personalia.consumer.Norg2Consumer
import no.nav.personopplysninger.testutils.createAccessToken
import no.nav.personopplysninger.testutils.endreKontonummerState
import org.apache.kafka.clients.producer.MockProducer
import java.util.concurrent.TimeUnit

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
        personopplysningerProxyTargetApp = "",
        pdlTargetApp = "",
        pdlMottakTargetApp = "",
        kontoregisterTargetApp = "",
    )

    val tokendingsService = DummyTokendingsService()
    val hendelseProducer = HendelseProducer(MockProducer(), "")

    val idporten: IDPorten = mockIdporten()

    val institusjonConsumer = InstitusjonConsumer(httpClient, env, tokendingsService)
    val kontaktinfoConsumer = KontaktinfoConsumer(httpClient, env, tokendingsService)
    val kontoregisterConsumer = KontoregisterConsumer(httpClient, env, tokendingsService)
    val kodeverkConsumer = KodeverkConsumer(httpClient, env)
    val medlConsumer = MedlConsumer(httpClient, env, tokendingsService)
    val norg2Consumer = Norg2Consumer(httpClient, env)
    val pdlConsumer = PdlConsumer(httpClient, env, tokendingsService)
    val pdlMottakConsumer = PdlMottakConsumer(httpClient, env, tokendingsService)

    val kodeverkService = KodeverkService(setupKodeverkCache(env), kodeverkConsumer)
    val pdlService = PdlService(pdlConsumer)
    val endreOpplysningerService =
        EndreOpplysningerService(
            pdlMottakConsumer,
            kodeverkService,
            kontoregisterConsumer,
            pdlService,
            hendelseProducer
        )
    val institusjonService = InstitusjonService(institusjonConsumer)
    val medlService = MedlService(medlConsumer, kodeverkService)
    val kontaktinformasjonService = KontaktinformasjonService(kontaktinfoConsumer, kodeverkService)
    val personaliaService = PersonaliaService(kodeverkService, norg2Consumer, kontoregisterConsumer, pdlService)

    private fun setupKodeverkCache(environment: Environment): Cache<String, Kodeverk> {
        return Caffeine.newBuilder()
            .maximumSize(environment.subjectNameCacheThreshold)
            .expireAfterWrite(environment.subjectNameCacheExpiryMinutes, TimeUnit.MINUTES)
            .build()
    }

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
}