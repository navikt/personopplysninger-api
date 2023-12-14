package no.nav.personopplysninger.endreopplysninger

import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Kontonummer
import no.nav.personopplysninger.common.consumer.kontoregister.exception.KontoregisterValidationException
import no.nav.personopplysninger.common.util.getAuthTokenFromCall
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.config.Idporten
import no.nav.personopplysninger.config.MetricsCollector
import no.nav.personopplysninger.config.Pkce
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import org.slf4j.LoggerFactory
import java.util.UUID

private val logger = LoggerFactory.getLogger("endreOpplysningerRoute")

fun Route.endreOpplysninger(
    endreOpplysningerService: EndreOpplysningerService,
    metricsCollector: MetricsCollector,
    idporten: Idporten
) {
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

    post("/endreKontonummerAuth") {
        try {

            val redirect: String = idporten.redirectUri
            val state: String = UUID.randomUUID().toString()
            val nonce: String = UUID.randomUUID().toString()
            val pkce = Pkce()

            val url: Url = idporten.authorizeUrl(redirect, state, nonce, pkce)

            // TODO
            //val kontonummer = call.receive<Kontonummer>()
            val kontonummer = Kontonummer(kilde = "conubia", utenlandskKontoInformasjon = null, value = "verear")

            val endreKontonummerState = EndreKontonummerState(state, nonce, pkce.verifier.value, kontonummer)
            val encoded: String = Json.encodeToString(endreKontonummerState)
            val encrypted: String = idporten.encrypt(encoded)

            call.response.cookies.append("endreKontonummerState", encrypted)
            call.respondRedirect(url)
        } catch (e: KontoregisterValidationException) {
            logger.error("Validering feilet ved endring av kontonummer", e)
            call.respond(HttpStatusCode.BadRequest, e.message ?: "Validering av kontonummer feilet")
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }

    get("/endreKontonummerCallback") {
        val decrypted: String = idporten.decrypt(call.request.cookies["endreKontonummerState"]!!)
        val endreKontonummerState: EndreKontonummerState = Json.decodeFromString(decrypted)

        // TODO - error handling
        // TODO - should clear state cookie if invalid
        val actualState: String = call.request.queryParameters["state"]
            ?: throw RuntimeException("no state in callback")
        val expectedState: String = endreKontonummerState.state

        if (actualState != expectedState) {
            throw RuntimeException("state mismatch")
        }

        val code: String = call.request.queryParameters["code"]
            ?: throw RuntimeException("no code in callback")
        val stepupToken: String = idporten.token(code, endreKontonummerState.codeVerifier, endreKontonummerState.nonce)

        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)
            val stepupFnr = getFnrFromToken(stepupToken)

            if (fnr != stepupFnr) {
                throw RuntimeException("fnr mismatch")
            }

            val kontonummer = endreKontonummerState.kontonummer

            endreOpplysningerService.endreKontonummer(authToken, fnr, kontonummer)

            if (kontonummer.utenlandskKontoInformasjon == null) {
                metricsCollector.ENDRE_NORSK_KONTONUMMER_COUNTER.inc()
            } else {
                metricsCollector.ENDRE_UTENLANDSK_KONTONUMMER_COUNTER.inc()
            }

            // TODO - should redirect back to some page on frontend?
            call.respond(mapOf("statusType" to "OK"))
        } catch (e: KontoregisterValidationException) {
            logger.error("Validering feilet ved endring av kontonummer", e)
            call.respond(HttpStatusCode.BadRequest, e.message ?: "Validering av kontonummer feilet")
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }

    post("/endreKontonummer") {
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

@Serializable
data class EndreKontonummerState(
    val state: String,
    val nonce: String,
    val codeVerifier: String,
    val kontonummer: Kontonummer,
)
