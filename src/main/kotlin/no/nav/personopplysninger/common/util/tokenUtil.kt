package no.nav.personopplysninger.common.util

import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.authorization
import no.nav.security.token.support.core.jwt.JwtToken

private const val PID_CLAIM = "pid"

fun getAuthTokenFromCall(call: ApplicationCall): String {
    return call.request.authorization()?.replace("Bearer ", "")
        ?: throw RuntimeException("Fant ikke token i cookie eller auth-header")
}

fun getFnrFromToken(token: String): String {
    return JwtToken(token).jwtTokenClaims.getStringClaim(PID_CLAIM)
}