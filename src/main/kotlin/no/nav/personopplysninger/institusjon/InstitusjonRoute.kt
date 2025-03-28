package no.nav.personopplysninger.institusjon

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.nav.personopplysninger.utils.getAuthTokenFromCall
import no.nav.personopplysninger.utils.getFnrFromToken
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("institusjonRoute")

fun Route.institusjon(institusjonService: InstitusjonService) {
    get("/institusjonsopphold") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)

            val resp = institusjonService.hentInstitusjonsopphold(authToken, fnr)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av institusjonsopphold", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}