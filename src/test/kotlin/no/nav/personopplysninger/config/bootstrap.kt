package no.nav.personopplysninger.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.routing.routing
import io.prometheus.client.CollectorRegistry
import no.nav.personopplysninger.endreopplysninger.endreOpplysninger
import no.nav.personopplysninger.institusjon.institusjon
import no.nav.personopplysninger.kontaktinformasjon.kontaktinformasjon
import no.nav.personopplysninger.medl.medl
import no.nav.personopplysninger.personalia.personalia


fun Application.testModule(appContext: TestApplicationContext) {

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
        endreOpplysninger(appContext.endreOpplysningerService, MetricsCollector(CollectorRegistry()))
        institusjon(appContext.institusjonService)
        medl(appContext.medlService)
        personalia(appContext.personaliaService)
        kontaktinformasjon(appContext.kontaktinformasjonService)
    }
}