package no.nav.personopplysninger.featuretoggles

import io.ktor.http.Cookie
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.plugins.origin
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.finn.unleash.UnleashContext
import no.nav.common.featuretoggle.UnleashClient
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.common.util.getSelvbetjeningTokenFromCall
import org.slf4j.LoggerFactory
import java.util.*


private val logger = LoggerFactory.getLogger("featureTogglesRoute")

private const val UNLEASH_COOKIE_NAME = "unleash-cookie"

fun Route.featureToggles(unleashClient: UnleashClient) {
    get("/feature-toggles") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fodselsnr = getFnrFromToken(selvbetjeningIdtoken)
            val sessionId = call.request.cookies[UNLEASH_COOKIE_NAME] ?: generateSessionId(call)

            val unleashContext = UnleashContext.builder()
                .userId(fodselsnr)
                .sessionId(sessionId)
                .remoteAddress(call.request.origin.remoteHost)
                .build()

            val features: List<String> = call.request.queryParameters.getAll("feature") ?: emptyList()

            val evaluation: Map<String, Boolean> = features.associateWith { feature ->
                unleashClient.isEnabled(feature, unleashContext)
            }

            call.respond(evaluation)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av navn", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}

private fun generateSessionId(call: ApplicationCall): String {
    val uuid = UUID.randomUUID()
    val sessionId =
        java.lang.Long.toHexString(uuid.mostSignificantBits) + java.lang.Long.toHexString(uuid.leastSignificantBits)
    val cookie = Cookie(name = UNLEASH_COOKIE_NAME, value = sessionId, path = "/", maxAge = -1)
    call.response.cookies.append(cookie)
    return sessionId
}

