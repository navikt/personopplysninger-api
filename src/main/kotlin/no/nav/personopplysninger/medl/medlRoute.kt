package no.nav.personopplysninger.medl

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.nav.personopplysninger.util.getAuthTokenFromCall
import no.nav.personopplysninger.util.getFnrFromToken
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("medlRoute")

fun Route.medl(medlService: MedlService) {
    get("/medl") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)

            val resp = medlService.hentMedlemskap(authToken, fnr)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av medlemskap", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}