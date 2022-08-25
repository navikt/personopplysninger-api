package no.nav.personopplysninger

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.mockk.coEvery
import io.mockk.mockkStatic
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.common.util.getSelvbetjeningTokenFromCall
import no.nav.personopplysninger.config.TestApplicationContext
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.config.testModule

fun main() {
    mockkStatic(::getSelvbetjeningTokenFromCall)
    coEvery { getSelvbetjeningTokenFromCall(any()) } returns "dummyToken"

    mockkStatic(::getFnrFromToken)
    coEvery { getFnrFromToken(any()) } returns "10108000398"

    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes")) {
        testModule(TestApplicationContext(setupMockedClient()))
    }.start(wait = true)
}