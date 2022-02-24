package no.nav.personopplysninger.util

import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder

fun getToken(): String {
    val claimsIssuer = "selvbetjening"
    val context = JaxrsTokenValidationContextHolder.getHolder()
    return context.tokenValidationContext.getJwtToken(claimsIssuer).tokenAsString
}
