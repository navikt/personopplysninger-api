package no.nav.personopplysninger.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import no.nav.personopplysninger.features.auth.auth
import no.nav.personopplysninger.features.endreopplysninger.endreOpplysninger
import no.nav.personopplysninger.features.institusjon.institusjon
import no.nav.personopplysninger.features.medl.medl
import no.nav.personopplysninger.features.personalia.personalia


fun Application.testModule(appContext: TestApplicationContext) {

    install(ContentNegotiation) {
        json(jsonConfig())
    }

    routing {
        auth(appContext.pdlConsumer)
        endreOpplysninger(appContext.endreOpplysningerService)
        institusjon(appContext.institusjonService)
        medl(appContext.medlService)
        personalia(appContext.peronaliaService)
    }
}

fun main() {
    // Todo: logging, fikse tokenUtil-metoder, auto-reload, kanskje flytte til egen fil
    embeddedServer(Netty, port = 8080) {
        testModule(TestApplicationContext(setupMockedClient()))
    }.start(wait = true)
}