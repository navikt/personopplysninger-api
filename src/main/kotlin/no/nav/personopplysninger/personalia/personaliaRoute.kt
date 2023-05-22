package no.nav.personopplysninger.personalia

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.common.util.getAuthTokenFromCall
import org.slf4j.LoggerFactory


private val logger = LoggerFactory.getLogger("personaliaRoute")

fun Route.personalia(personaliaService: PersonaliaService) {
    get("/personalia") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)

            val resp = personaliaService.hentPersoninfo(authToken, fnr)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av personalia", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}