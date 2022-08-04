package no.nav.personopplysninger.features.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.util.getFnrFromToken
import no.nav.personopplysninger.util.getSelvbetjeningTokenFromCall
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("authRoute")

fun Route.auth(pdlConsumer: PdlConsumer) {
    get("/name") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val navn =
                pdlConsumer.getNavn(selvbetjeningIdtoken, getFnrFromToken(selvbetjeningIdtoken)).navn.firstOrNull()
            val fulltNavn = listOf(navn?.fornavn, navn?.mellomnavn, navn?.etternavn).filter { !it.isNullOrEmpty() }
                .joinToString(separator = " ")

            call.respond(mapOf("name" to fulltNavn))
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av navn", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}