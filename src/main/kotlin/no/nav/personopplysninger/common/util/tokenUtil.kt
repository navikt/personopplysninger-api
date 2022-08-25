package no.nav.personopplysninger.common.util

import io.ktor.server.application.ApplicationCall
import no.nav.security.token.support.core.jwt.JwtToken

private const val PID_CLAIM = "pid"
private const val OIDC_COOKIE_NAME = "selvbetjening-idtoken"

fun getSelvbetjeningTokenFromCall(call: ApplicationCall): String {
    return call.request.cookies[OIDC_COOKIE_NAME]!!
}

fun getFnrFromToken(token: String): String {
    return JwtToken(token).jwtTokenClaims.getStringClaim(PID_CLAIM)
}