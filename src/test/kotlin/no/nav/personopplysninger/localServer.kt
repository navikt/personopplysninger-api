package no.nav.personopplysninger

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.mockk.coEvery
import io.mockk.mockkStatic
import no.nav.personopplysninger.common.util.getAuthTokenFromCall
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.config.TestApplicationContext
import no.nav.personopplysninger.config.setupMockedClient
import no.nav.personopplysninger.config.testModule

fun main() {
    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes")) {
        mockkStatic(::getAuthTokenFromCall)
        mockkStatic(::getFnrFromToken)

        coEvery { getAuthTokenFromCall(any()) } returns "dummyToken"
        coEvery { getFnrFromToken(any()) } returns "10108000398"

        testModule(TestApplicationContext(setupMockedClient()))
    }.start(wait = true)
}