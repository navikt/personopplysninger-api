package no.nav.personopplysninger.features.endreopplysninger

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.features.endreopplysninger.dto.Kontonummer
import no.nav.personopplysninger.util.getFnrFromToken
import no.nav.personopplysninger.util.getSelvbetjeningTokenFromCall
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("endreOpplysningerRoute")

fun Route.endreOpplysninger(endreOpplysningerService: EndreOpplysningerService) {
    post("/endreTelefonnummer") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fnr = getFnrFromToken(selvbetjeningIdtoken)
            val telefonnummer = call.receive<Telefonnummer>()

            val resp = endreOpplysningerService.endreTelefonnummer(selvbetjeningIdtoken, fnr, telefonnummer)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av telefonnummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    post("/slettTelefonnummer") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fnr = getFnrFromToken(selvbetjeningIdtoken)
            val telefonnummer = call.receive<Telefonnummer>()

            val resp = endreOpplysningerService.slettTelefonNummer(selvbetjeningIdtoken, fnr, telefonnummer)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved sletting av telefonnummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    post("/endreKontonummer") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fnr = getFnrFromToken(selvbetjeningIdtoken)
            val kontonummer = call.receive<Kontonummer>()

            endreOpplysningerService.endreKontonummer(selvbetjeningIdtoken, fnr, kontonummer)
            call.respond(mapOf("statusType" to "OK"))
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    post("/slettKontaktadresse") {
        try {
            val selvbetjeningIdtoken = getSelvbetjeningTokenFromCall(call)
            val fnr = getFnrFromToken(selvbetjeningIdtoken)

            val resp = endreOpplysningerService.slettKontaktadresse(selvbetjeningIdtoken, fnr)
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved sletting av kontaktadresse", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/retningsnumre") {
        try {
            val resp = endreOpplysningerService.hentRetningsnumre()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av retningsnumre", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/land") {
        try {
            val resp = endreOpplysningerService.hentLand()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av land", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/valuta") {
        try {
            val resp = endreOpplysningerService.hentValuta()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av valuta", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/postnummer") {
        try {
            val resp = endreOpplysningerService.hentPostnummer()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av postnummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
}