package no.nav.personopplysninger.config

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.ktor.client.HttpClient
import no.nav.personopplysninger.consumer.inst.InstitusjonConsumer
import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.consumer.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.consumer.kontaktinformasjon.KontaktinfoConsumer
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.norg2.Norg2Consumer
import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.consumer.pdl.PdlService
import no.nav.personopplysninger.consumer.pdlmottak.PdlMottakConsumer
import no.nav.personopplysninger.features.endreopplysninger.EndreOpplysningerService
import no.nav.personopplysninger.features.institusjon.InstitusjonService
import no.nav.personopplysninger.features.kontaktinformasjon.KontaktinformasjonService
import no.nav.personopplysninger.features.medl.MedlService
import no.nav.personopplysninger.features.personalia.PersonaliaService
import java.util.concurrent.TimeUnit

class TestApplicationContext(httpClient: HttpClient) {

    val env = Environment()

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