package no.nav.personopplysninger.util

import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder

private const val CLAIMS_ISSUER = "selvbetjening"
private const val PID_CLAIM_KEY = "pid"

fun getToken(): String {
    val context = JaxrsTokenValidationContextHolder.getHolder()
    return context.tokenValidationContext.getJwtToken(CLAIMS_ISSUER).tokenAsString
}

fun hentFnrFraToken(): String {
    val context = JaxrsTokenValidationContextHolder.getHolder()
    return context.tokenValidationContext.getClaims(CLAIMS_ISSUER).getStringClaim(PID_CLAIM_KEY)
}