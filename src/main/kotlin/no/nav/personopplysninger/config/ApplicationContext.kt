package no.nav.personopplysninger.config

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
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
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.spec.SecretKeySpec

class ApplicationContext {

    val env = Environment()
    val httpClient = HttpClientBuilder.build()

    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    val metricsCollector = MetricsCollector(appMicrometerRegistry.prometheusRegistry)

    val tokendingsService = TokendingsServiceBuilder.buildTokendingsService()
    val hendelseProducer = HendelseProducer(initializeKafkaProducer(env), env.varselHendelseTopic)

    val idporten = setupIdporten(env)

    val institusjonConsumer = InstitusjonConsumer(httpClient, env, tokendingsService)
    val kontaktinfoConsumer = KontaktinfoConsumer(httpClient, env, tokendingsService)
    val kontoregisterConsumer = KontoregisterConsumer(httpClient, env, tokendingsService)
    val kodeverkConsumer = KodeverkConsumer(httpClient, env)
    val medlConsumer = MedlConsumer(httpClient, env, tokendingsService)
    val norg2Consumer = Norg2Consumer(httpClient, env)
    val pdlConsumer = PdlConsumer(GraphQLKtorClient(URI(env.pdlUrl).toURL(), httpClient), env, tokendingsService)
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

    private fun setupKodeverkCache(environment: Environment): Cache<String, Kodeverk> {
        return Caffeine.newBuilder()
            .maximumSize(environment.subjectNameCacheThreshold)
            .expireAfterWrite(environment.subjectNameCacheExpiryMinutes, TimeUnit.MINUTES)
            .build()
    }
}