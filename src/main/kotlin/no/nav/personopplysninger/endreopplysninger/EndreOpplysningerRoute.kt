package no.nav.personopplysninger.endreopplysninger

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.takeFrom
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import no.nav.personopplysninger.config.MetricsCollector
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Kontonummer
import no.nav.personopplysninger.consumer.kontoregister.exception.KontoregisterValidationException
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.endreopplysninger.idporten.EndreKontonummerState
import no.nav.personopplysninger.endreopplysninger.idporten.IDPortenException
import no.nav.personopplysninger.endreopplysninger.idporten.IDPortenService
import no.nav.personopplysninger.utils.getAuthTokenFromCall
import no.nav.personopplysninger.utils.getFnrFromToken
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("endreOpplysningerRoute")

private const val KONTONR_RESULT_COOKIE = "kontonr-result"

fun Route.endreOpplysninger(
    endreOpplysningerService: EndreOpplysningerService,
    metricsCollector: MetricsCollector,
    idportenService: IDPortenService
) {
    post("/endreTelefonnummer") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)
            val telefonnummer = call.receive<Telefonnummer>()

            val resp = endreOpplysningerService.endreTelefonnummer(authToken, fnr, telefonnummer)
            metricsCollector.endreTelefonnummerCounter.inc()
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
            metricsCollector.slettTelefonnummerCounter.inc()
            call.respond(resp)
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved sletting av telefonnummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }

    post("/endreKontonummer") {
        try {
            val (state, nonce, pkce) = idportenService.createParams()
            val cookie = idportenService.createCookie(
                EndreKontonummerState(
                    state = state,
                    nonce = nonce,
                    codeVerifier = pkce.verifier.value,
                    kontonummer = call.receive<Kontonummer>(),
                    bruker = getFnrFromToken(getAuthTokenFromCall(call)),
                    locale = call.request.header("locale") ?: "nb"
                )
            )
            val authorizeUrl = idportenService.createAuthorizeUrl(state, nonce, pkce)

            call.response.cookies.append(cookie)
            call.response.headers.append(HttpHeaders.Location, authorizeUrl.toString())
            call.respond(HttpStatusCode.OK, mapOf("redirect" to authorizeUrl.toString()))

        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }


    get("/lagreKontonummer") {
        var locale = "nb"

        try {
            call.response.cookies.append(idportenService.createExpiredCookie())

            val endreKontonummerState = idportenService.createEndreKontonummerState(
                call.request.cookies["endreKontonummerState"]
            )

            locale = endreKontonummerState.locale

            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)

            idportenService.validateRequest(call.request, endreKontonummerState, fnr)

            endreKontonummerState.kontonummer.let { kontonummer ->
                endreOpplysningerService.endreKontonummer(authToken, fnr, kontonummer)

                if (kontonummer.utenlandskKontoInformasjon == null) {
                    metricsCollector.endreNorskKontonummerCounter.inc()
                } else {
                    metricsCollector.endreUtenlandskKontonummerCounter.inc()
                }
            }

            call.withResultCookie("success")
            call.respondRedirect(idportenService.frontendUriWithLocale(locale))
        } catch (e: KontoregisterValidationException) {
            logger.error("Validering feilet ved endring av kontonummer", e)
            call.handleEndreKontonummerException(
                idportenService.frontendUriWithLocale(locale),
                "validation",
                HttpStatusCode.BadRequest
            )
        } catch (e: IDPortenException) {
            logger.error("Feil ved autentisering", e)
            call.handleEndreKontonummerException(
                idportenService.frontendUriWithLocale(locale),
                "auth",
                HttpStatusCode.Unauthorized
            )
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.handleEndreKontonummerException(
                idportenService.frontendUriWithLocale(locale),
                "unexpected",
                HttpStatusCode.InternalServerError
            )
        }
    }
    post("/slettKontaktadresse") {
        try {
            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)

            val resp = endreOpplysningerService.slettKontaktadresse(authToken, fnr)
            metricsCollector.slettKontaktadresseCounter
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

private fun ApplicationCall.withResultCookie(result: String) {
    response.cookies.append(KONTONR_RESULT_COOKIE, result, path = "/", domain = ".nav.no")
}

private suspend fun ApplicationCall.handleEndreKontonummerException(
    uri: Url,
    error: String,
    statusCode: HttpStatusCode
) {
    val u = URLBuilder().takeFrom(uri).apply {
        parameters.append("error", error)
        parameters.append("status", statusCode.value.toString())
    }

    withResultCookie("error")

    respondRedirect(u.build())
}