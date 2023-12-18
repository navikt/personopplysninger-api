package no.nav.personopplysninger.endreopplysninger

import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.util.date.GMTDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Kontonummer
import no.nav.personopplysninger.common.consumer.kontoregister.exception.KontoregisterValidationException
import no.nav.personopplysninger.common.util.getAuthTokenFromCall
import no.nav.personopplysninger.common.util.getFnrFromToken
import no.nav.personopplysninger.config.IDPorten
import no.nav.personopplysninger.config.IDPortenException
import no.nav.personopplysninger.config.MetricsCollector
import no.nav.personopplysninger.config.Pkce
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import org.slf4j.LoggerFactory
import java.util.UUID

private val logger = LoggerFactory.getLogger("endreOpplysningerRoute")
private val allowedLocales = setOf("nb", "nn", "en")

private const val KONTONR_RESULT_COOKIE = "kontonr-result"

fun Route.endreOpplysninger(
    endreOpplysningerService: EndreOpplysningerService,
    metricsCollector: MetricsCollector,
    idporten: IDPorten
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

    post("/endreKontonummer") {
        try {
            val locale: String = call.request.header("locale") ?: "nb"
            val state: String = UUID.randomUUID().toString()
            val nonce: String = UUID.randomUUID().toString()
            val pkce = Pkce()

            val url: Url = idporten.authorizeUrl(state, nonce, pkce)

            val kontonummer = call.receive<Kontonummer>()
            val authToken = getAuthTokenFromCall(call)
            val bruker = getFnrFromToken(authToken)
            val endreKontonummerState =
                EndreKontonummerState(state, nonce, pkce.verifier.value, kontonummer, bruker, locale)
            val encoded: String = Json.encodeToString(endreKontonummerState)
            val encrypted: String = idporten.encrypt(encoded)

            call.response.cookies.append(
                "endreKontonummerState",
                encrypted,
                httpOnly = true,
                secure = idporten.secureCookie,
                extensions = mapOf("SameSite" to "Lax"),
            )

            call.response.headers.append("Location", url.toString())
            call.respond(HttpStatusCode.OK, mapOf("redirect" to url.toString()))
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
        }
    }
    get("/lagreKontonummer") {
        var locale = "nb"

        try {
            call.response.cookies.append(
                "endreKontonummerState",
                "",
                maxAge = 0L,
                expires = GMTDate.START,
                httpOnly = true,
                secure = idporten.secureCookie,
                extensions = mapOf("SameSite" to "Lax"),
            )

            val decrypted: String = idporten.decrypt(call.request.cookies["endreKontonummerState"]!!)
            val endreKontonummerState: EndreKontonummerState = Json.decodeFromString(decrypted)
            locale = endreKontonummerState.locale

            // TODO - state cookie should have some expiry or validity period

            val actualState: String = call.request.queryParameters["state"]
                ?: throw IDPortenException("no state in callback")
            val expectedState: String = endreKontonummerState.state

            if (actualState != expectedState) {
                throw IDPortenException("state mismatch")
            }

            val code: String = call.request.queryParameters["code"]
                ?: throw IDPortenException("no code in callback")
            val reauthToken: String =
                idporten.token(code, endreKontonummerState.codeVerifier, endreKontonummerState.nonce)

            val authToken = getAuthTokenFromCall(call)
            val fnr = getFnrFromToken(authToken)
            val reauthFnr = getFnrFromToken(reauthToken)
            val stateFnr = endreKontonummerState.bruker

            if (listOf(fnr, reauthFnr, stateFnr).distinct().size > 1) {
                throw IDPortenException("fnr mismatch")
            }

            val kontonummer = endreKontonummerState.kontonummer

            endreOpplysningerService.endreKontonummer(authToken, fnr, kontonummer)

            if (kontonummer.utenlandskKontoInformasjon == null) {
                metricsCollector.ENDRE_NORSK_KONTONUMMER_COUNTER.inc()
            } else {
                metricsCollector.ENDRE_UTENLANDSK_KONTONUMMER_COUNTER.inc()
            }

            val url = URLBuilder()
                .takeFrom(idporten.frontendUri.withLocale(locale))
                .build()

            call.withResultCookie("success")

            call.respondRedirect(url)
        } catch (e: KontoregisterValidationException) {
            logger.error("Validering feilet ved endring av kontonummer", e)
            call.handleEndreKontonummerException(
                idporten.frontendUri.withLocale(locale),
                "validation",
                HttpStatusCode.BadRequest
            )
        } catch (e: IDPortenException) {
            logger.error("Feil ved autentisering", e)
            call.handleEndreKontonummerException(
                idporten.frontendUri.withLocale(locale),
                "auth",
                HttpStatusCode.Unauthorized
            )
        } catch (e: Exception) {
            logger.error("Noe gikk galt ved endring av kontonummer", e)
            call.handleEndreKontonummerException(
                idporten.frontendUri.withLocale(locale),
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

private fun Url.withLocale(locale: String): Url =
    URLBuilder().takeFrom(this).apply {
        appendPathSegments(allowedLocales.firstOrNull { it == locale } ?: "nb", "endre-kontonummer")
    }.build()

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

@Serializable
data class EndreKontonummerState(
    val state: String,
    val nonce: String,
    val codeVerifier: String,
    val kontonummer: Kontonummer,
    val bruker: String,
    val locale: String
)
