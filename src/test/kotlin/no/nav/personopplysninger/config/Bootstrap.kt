package no.nav.personopplysninger.config

import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.routing.routing
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import no.nav.personopplysninger.endreopplysninger.endreOpplysninger
import no.nav.personopplysninger.institusjon.institusjon
import no.nav.personopplysninger.kontaktinformasjon.kontaktinformasjon
import no.nav.personopplysninger.medl.medl
import no.nav.personopplysninger.personalia.personalia

fun Application.testModule(appContext: TestApplicationContext) {

    install(CORS) {
        allowHost("*", schemes = listOf("http"))
        allowCredentials = true
        allowHeader(HttpHeaders.ContentType)
    }

    install(ContentNegotiation) {
        json(jsonConfig())
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

    routing {
        endreOpplysninger(
            appContext.endreOpplysningerService,
            MetricsCollector(PrometheusMeterRegistry(PrometheusConfig.DEFAULT).prometheusRegistry),
            appContext.idporten
        )
        institusjon(appContext.institusjonService)
        medl(appContext.medlService)
        personalia(appContext.personaliaService)
        kontaktinformasjon(appContext.kontaktinformasjonService)
    }
}