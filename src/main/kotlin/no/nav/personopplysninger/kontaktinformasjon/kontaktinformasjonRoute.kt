package no.nav.personopplysninger.kontaktinformasjon

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.common.util.getAuthTokenFromCall
import org.slf4j.LoggerFactory


private val logger = LoggerFactory.getLogger("kontaktinformasjonRoute")

fun Route.kontaktinformasjon(kontaktinformasjonService: KontaktinformasjonService) {
    get("/kontaktinformasjon") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)

            val resp = kontaktinformasjonService.hentKontaktinformasjon(authToken, fnr)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av kontaktinformasjon", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}