package no.nav.personopplysninger.config

import no.finn.unleash.strategy.Strategy
import no.nav.common.featuretoggle.UnleashClient
import no.nav.common.featuretoggle.UnleashClientImpl
import no.nav.common.featuretoggle.UnleashUtils
import no.nav.common.utils.EnvironmentUtils
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
import no.nav.personopplysninger.features.featuretoggles.ByApplicationStrategy
import no.nav.personopplysninger.features.institusjon.InstitusjonService
import no.nav.personopplysninger.features.medl.MedlService
import no.nav.personopplysninger.features.personalia.PersonaliaService
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder
import java.util.*

class ApplicationContext {

    val env = Environment()
    val httpClient = HttpClientBuilder.build()

    val tokendingsService = TokendingsServiceBuilder.buildTokendingsService()
    val unleashClient = unleashClient()

    val institusjonConsumer = InstitusjonConsumer(httpClient, env, tokendingsService)
    val kodeverkConsumer = KodeverkConsumer(httpClient, env)
    val kontaktinfoConsumer = KontaktinfoConsumer(httpClient, env, tokendingsService)
    val kontoregisterConsumer = KontoregisterConsumer(httpClient, env, tokendingsService)
    val medlConsumer = MedlConsumer(httpClient, env, tokendingsService)
    val norg2Consumer = Norg2Consumer(httpClient, env, tokendingsService)
    val pdlConsumer = PdlConsumer(httpClient, env, tokendingsService)
    val pdlMottakConsumer = PdlMottakConsumer(httpClient, env, tokendingsService)

    val pdlService = PdlService(pdlConsumer)
    val endreOpplysningerService =
        EndreOpplysningerService(pdlMottakConsumer, kodeverkConsumer, kontoregisterConsumer, pdlService)
    val institusjonService = InstitusjonService(institusjonConsumer)
    val medlService = MedlService(medlConsumer, kodeverkConsumer)
    val peronaliaService =
        PersonaliaService(kontaktinfoConsumer, kodeverkConsumer, norg2Consumer, kontoregisterConsumer, pdlService)


    private fun unleashClient(): UnleashClient {
        return UnleashClientImpl(
            EnvironmentUtils.getOptionalProperty(UnleashUtils.UNLEASH_URL_ENV_NAME)
                .orElse("https://unleash.nais.io/api/"),
            EnvironmentUtils.getRequiredProperty("NAIS_APP_NAME"),
            Collections.singletonList(ByApplicationStrategy()) as List<Strategy>?
        )
    }
}