package no.nav.personopplysninger.testutils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

fun createAccessToken(fnr: String = FNR): String {
    return JWT.create().withClaim("pid", fnr).sign(Algorithm.HMAC256("1"))
}