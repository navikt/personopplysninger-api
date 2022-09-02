package no.nav.personopplysninger.config

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.ktor.client.HttpClient
import no.nav.personopplysninger.common.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.common.kodeverk.KodeverkService
import no.nav.personopplysninger.common.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.common.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.common.pdl.PdlConsumer
import no.nav.personopplysninger.common.pdl.PdlService
import no.nav.personopplysninger.endreopplysninger.EndreOpplysningerService
import no.nav.personopplysninger.endreopplysninger.consumer.PdlMottakConsumer
import no.nav.personopplysninger.institusjon.InstitusjonService
import no.nav.personopplysninger.institusjon.consumer.InstitusjonConsumer
import no.nav.personopplysninger.kontaktinformasjon.KontaktinformasjonService
import no.nav.personopplysninger.kontaktinformasjon.consumer.KontaktinfoConsumer
import no.nav.personopplysninger.medl.MedlService
import no.nav.personopplysninger.medl.consumer.MedlConsumer
import no.nav.personopplysninger.personalia.PersonaliaService
import no.nav.personopplysninger.personalia.consumer.Norg2Consumer
import java.util.concurrent.TimeUnit

class TestApplicationContext(httpClient: HttpClient) {

    val env = Environment(
        corsAllowedOrigins = "",
        corsAllowedSchemes = "https",
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

    val institusjonConsumer = InstitusjonConsumer(httpClient, env, tokendingsService)
    val kontaktinfoConsumer = KontaktinfoConsumer(httpClient, env, tokendingsService)
    val kontoregisterConsumer = KontoregisterConsumer(httpClient, env, tokendingsService)
    val kodeverkConsumer = KodeverkConsumer(httpClient, env)
    val medlConsumer = MedlConsumer(httpClient, env, tokendingsService)
    val norg2Consumer = Norg2Consumer(httpClient, env, tokendingsService)
    val pdlConsumer = PdlConsumer(httpClient, env, tokendingsService)
    val pdlMottakConsumer = PdlMottakConsumer(httpClient, env, tokendingsService)

    val kodeverkService = KodeverkService(setupKodeverkCache(env), kodeverkConsumer)
    val pdlService = PdlService(pdlConsumer)
    val endreOpplysningerService =
        EndreOpplysningerService(pdlMottakConsumer, kodeverkService, kontoregisterConsumer, pdlService)
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
}