package no.nav.personopplysninger.endreopplysninger

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Kontonummer
import no.nav.personopplysninger.common.consumer.kontoregister.exception.KontoregisterValidationException
import no.nav.personopplysninger.common.util.getAuthTokenFromCall
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.config.MetricsCollector
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("endreOpplysningerRoute")

fun Route.endreOpplysninger(endreOpplysningerService: EndreOpplysningerService, metricsCollector: MetricsCollector) {
    post("/endreTelefonnummer") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)
            val telefonnummer = call.receive<Telefonnummer>()

            val resp = endreOpplysningerService.endreTelefonnummer(authToken, fnr, telefonnummer)
            metricsCollector.ENDRE_TELEFONNUMMER_COUNTER.inc()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av telefonnummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    post("/slettTelefonnummer") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)
            val telefonnummer = call.receive<Telefonnummer>()

            val resp = endreOpplysningerService.slettTelefonNummer(authToken, fnr, telefonnummer)
            metricsCollector.SLETT_TELEFONNUMMER_COUNTER.inc()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved sletting av telefonnummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/endreKontonummerCallback"){
        // get token and actually store data
    }

    post("/endreKontonummerAuth"){

    }

    post("/endreKontonummer") {
        // trigger auth from here
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)
            val kontonummer = call.receive<Kontonummer>()

            endreOpplysningerService.endreKontonummer(authToken, fnr, kontonummer)

            if (kontonummer.utenlandskKontoInformasjon == null) {
                metricsCollector.ENDRE_NORSK_KONTONUMMER_COUNTER.inc()
            } else {
                metricsCollector.ENDRE_UTENLANDSK_KONTONUMMER_COUNTER.inc()
            }

            call.respond(mapOf("statusType" to "OK"))
        } catch (e: KontoregisterValidationException) {
            logger.error("Validering feilet ved endring av kontonummer", e)
            call.respond(HttpStatusCode.BadRequest, e.message ?: "Validering av kontonummer feilet")
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    post("/slettKontaktadresse") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)

            val resp = endreOpplysningerService.slettKontaktadresse(authToken, fnr)
            metricsCollector.SLETT_KONTAKTADRESSE_COUNTER
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
            val resp = endreOpplysningerService.hentLandkoder()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved henting av land", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/valuta") {
        try {
            val resp = endreOpplysningerService.hentValutakoder()
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