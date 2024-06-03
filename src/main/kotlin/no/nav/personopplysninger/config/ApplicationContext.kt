package no.nav.personopplysninger.config

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import no.nav.personopplysninger.consumer.digdirkrr.KontaktinfoConsumer
import no.nav.personopplysninger.consumer.inst2.InstitusjonConsumer
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.norg2.Norg2Consumer
import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.consumer.pdl.PdlService
import no.nav.personopplysninger.consumer.pdlmottak.PdlMottakConsumer
import no.nav.personopplysninger.endreopplysninger.EndreOpplysningerService
import no.nav.personopplysninger.endreopplysninger.kafka.HendelseProducer
import no.nav.personopplysninger.institusjon.InstitusjonService
import no.nav.personopplysninger.kontaktinformasjon.KontaktinformasjonService
import no.nav.personopplysninger.medl.MedlService
import no.nav.personopplysninger.personalia.PersonaliaService
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder
import java.net.URI
import java.util.*
import javax.crypto.spec.SecretKeySpec

class ApplicationContext {

    val env = Environment()
    val httpClient = HttpClientBuilder.build()
    val cachedHttpClient = HttpClientBuilder.build(cached = true)

    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    val metricsCollector = MetricsCollector(appMicrometerRegistry.prometheusRegistry)

    val tokendingsService = TokendingsServiceBuilder.buildTokendingsService()
    val hendelseProducer = HendelseProducer(initializeKafkaProducer(env), env.varselHendelseTopic)

    val idporten = setupIdporten(env)

    val institusjonConsumer = InstitusjonConsumer(httpClient, env, tokendingsService)
    val kontaktinfoConsumer = KontaktinfoConsumer(httpClient, env, tokendingsService)
    val kontoregisterConsumer = KontoregisterConsumer(httpClient, env, tokendingsService)
    val kodeverkConsumer = no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer(cachedHttpClient, env)
    val medlConsumer = MedlConsumer(httpClient, env, tokendingsService)
    val norg2Consumer = Norg2Consumer(httpClient, env)
    val pdlConsumer = PdlConsumer(GraphQLKtorClient(URI(env.pdlUrl).toURL(), httpClient), env, tokendingsService)
    val pdlMottakConsumer = PdlMottakConsumer(httpClient, env, tokendingsService)

    val pdlService = PdlService(pdlConsumer)

    val endreOpplysningerService =
        EndreOpplysningerService(
            pdlMottakConsumer,
            kodeverkConsumer,
            kontoregisterConsumer,
            pdlService,
            hendelseProducer
        )
    val institusjonService = InstitusjonService(institusjonConsumer)
    val medlService = MedlService(medlConsumer, kodeverkConsumer)
    val kontaktinformasjonService = KontaktinformasjonService(kontaktinfoConsumer, kodeverkConsumer)
    val personaliaService = PersonaliaService(kodeverkConsumer, norg2Consumer, kontoregisterConsumer, pdlService)


    private fun setupIdporten(env: Environment): IDPorten {
        return IDPorten(
            redirectUri = env.redirectUri,
            frontendUri = URLBuilder().takeFrom(env.frontendUri).build(),
            wellKnownUrl = env.wellKnownUrl,
            clientId = env.clientId,
            clientJwk = env.clientJwk,
            encryptionKey = SecretKeySpec(
                Base64.getDecoder().decode(env.encryptionKey),
                "AES"
            ),
        )
    }
}