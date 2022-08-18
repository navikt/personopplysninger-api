package no.nav.personopplysninger.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.HttpClient
import no.nav.personopplysninger.consumer.inst.InstitusjonConsumer
import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kontaktinformasjon.KontaktinfoConsumer
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.norg2.Norg2Consumer
import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.consumer.pdl.PdlService
import no.nav.personopplysninger.consumer.pdlmottak.PdlMottakConsumer
import no.nav.personopplysninger.features.endreopplysninger.EndreOpplysningerService
import no.nav.personopplysninger.features.institusjon.InstitusjonService
import no.nav.personopplysninger.features.medl.MedlService
import no.nav.personopplysninger.features.personalia.PersonaliaService

class TestApplicationContext(httpClient: HttpClient) {

    val env = Environment()
    val objectMapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerModule(JavaTimeModule())
    }


    val tokendingsService = DummyTokendingsService()

    val institusjonConsumer = InstitusjonConsumer(httpClient, env, tokendingsService)
    val kodeverkConsumer = KodeverkConsumer(httpClient, env)
    val kontaktinfoConsumer = KontaktinfoConsumer(httpClient, env, tokendingsService)
    val kontoregisterConsumer = KontoregisterConsumer(httpClient, env, tokendingsService)
    val medlConsumer = MedlConsumer(httpClient, env, tokendingsService)
    val norg2Consumer = Norg2Consumer(httpClient, env, tokendingsService)
    val pdlConsumer = PdlConsumer(httpClient, env, tokendingsService)
    val pdlMottakConsumer = PdlMottakConsumer(httpClient, env, tokendingsService, objectMapper)

    val pdlService = PdlService(pdlConsumer)
    val endreOpplysningerService =
        EndreOpplysningerService(pdlMottakConsumer, kodeverkConsumer, kontoregisterConsumer, pdlService)
    val institusjonService = InstitusjonService(institusjonConsumer)
    val medlService = MedlService(medlConsumer, kodeverkConsumer)
    val peronaliaService =
        PersonaliaService(kontaktinfoConsumer, kodeverkConsumer, norg2Consumer, kontoregisterConsumer, pdlService)
}