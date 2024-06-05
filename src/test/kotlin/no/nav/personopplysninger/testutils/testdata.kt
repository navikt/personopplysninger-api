package no.nav.personopplysninger.testutils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Kontonummer
import no.nav.personopplysninger.endreopplysninger.EndreKontonummerState

const val STATE = "state"
const val FNR = "12345678911"

fun endreKontonummerState(): EndreKontonummerState {
    return EndreKontonummerState(
        state = STATE,
        nonce = "",
        codeVerifier = "",
        kontonummer = kontonummer(),
        bruker = FNR,
        locale = "",
    )
}

fun kontonummer(): Kontonummer {
    return Kontonummer("kilde", null, FNR)
}

fun createAccessToken(fnr: String = FNR): String {
    return JWT.create().withClaim("pid", fnr).sign(Algorithm.HMAC256("1"))
}