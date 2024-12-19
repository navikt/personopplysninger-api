package no.nav.personopplysninger.config

import io.ktor.client.HttpClient
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.metrics.micrometer.MicrometerMetrics
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.forwardedheaders.XForwardedHeaders
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.uri
import io.ktor.server.routing.routing
import no.nav.personopplysninger.endreopplysninger.endreOpplysninger
import no.nav.personopplysninger.health.health
import no.nav.personopplysninger.institusjon.institusjon
import no.nav.personopplysninger.kontaktinformasjon.kontaktinformasjon
import no.nav.personopplysninger.medl.medl
import no.nav.personopplysninger.personalia.personalia
import no.nav.security.token.support.v3.RequiredClaims
import no.nav.security.token.support.v3.tokenValidationSupport
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("personopplysninger-api")

fun Application.mainModule(appContext: ApplicationContext = ApplicationContext()) {
    val environment = Environment()

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { cause ->
            logger.warn(cause.description + ": " + call.request.uri)
        }
    }

    val conf = this.environment.config

    install(Authentication) {
        tokenValidationSupport(
            config = conf,
            requiredClaims = RequiredClaims(
                issuer = "idporten",
                claimMap = arrayOf("acr=Level4", "acr=idporten-loa-high"),
                combineWithOr = true
            )
        )
    }

    install(XForwardedHeaders)

    install(CallLogging) {
        filter { call -> !call.request.path().contains("internal") }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val path = call.request.path()
            "$status - $httpMethod $path"
        }
    }

    install(MicrometerMetrics) {
        registry = appContext.appMicrometerRegistry
    }

    install(ContentNegotiation) {
        json(jsonConfig())
    }

    install(CORS) {
        allowHost(environment.corsAllowedOrigins, schemes = listOf(environment.corsAllowedSchemes))
        allowCredentials = true
        allowHeader(HttpHeaders.ContentType)
    }

    routing {
        health(appContext.appMicrometerRegistry)
        authenticate {
            endreOpplysninger(appContext.endreOpplysningerService, appContext.metricsCollector, appContext.idportenService)
            institusjon(appContext.institusjonService)
            medl(appContext.medlService)
            personalia(appContext.personaliaService)
            kontaktinformasjon(appContext.kontaktinformasjonService)
        }
    }

    configureShutdownHook(appContext.httpClient)
}

private fun Application.configureShutdownHook(httpClient: HttpClient) {
    environment.monitor.subscribe(ApplicationStopping) {
        httpClient.close()
    }
}
