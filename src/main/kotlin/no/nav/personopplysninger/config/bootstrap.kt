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
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.uri
import io.ktor.server.routing.routing
import no.nav.personopplysninger.features.auth.auth
import no.nav.personopplysninger.features.endreopplysninger.endreOpplysninger
import no.nav.personopplysninger.features.featuretoggles.featureToggles
import no.nav.personopplysninger.features.institusjon.institusjon
import no.nav.personopplysninger.features.kontaktinformasjon.kontaktinformasjon
import no.nav.personopplysninger.features.medl.medl
import no.nav.personopplysninger.features.personalia.personalia
import no.nav.personopplysninger.health.health
import no.nav.security.token.support.v2.RequiredClaims
import no.nav.security.token.support.v2.tokenValidationSupport
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
                issuer = "loginservice",
                claimMap = arrayOf("acr=Level4")
            )
        )
    }

    install(CallLogging) {
        filter { call -> !call.request.path().contains("internal") }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val path = call.request.path()
            "$status - $httpMethod $path"
        }
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
        health()
        authenticate {
            auth(appContext.pdlConsumer)
            endreOpplysninger(appContext.endreOpplysningerService)
            featureToggles(appContext.unleashClient)
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
