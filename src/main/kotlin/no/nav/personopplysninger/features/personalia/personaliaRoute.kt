package no.nav.personopplysninger.features.personalia

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.nav.personopplysninger.util.getFnrFromToken
import no.nav.personopplysninger.util.getSelvbetjeningTokenFromCall
import org.slf4j.LoggerFactory


private val logger = LoggerFactory.getLogger("personaliaRoute")

fun Route.personalia(personaliaService: PersonaliaService) {
    get("/personalia") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fnr = getFnrFromToken(selvbetjeningIdtoken)

            val resp = personaliaService.hentPersoninfo(selvbetjeningIdtoken, fnr)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av personalia", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/kontaktinformasjon") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fnr = getFnrFromToken(selvbetjeningIdtoken)

            val resp = personaliaService.hentKontaktinformasjon(selvbetjeningIdtoken, fnr)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av kontaktinformasjon", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}