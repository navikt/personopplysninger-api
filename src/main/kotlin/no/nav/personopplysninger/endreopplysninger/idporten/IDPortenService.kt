package no.nav.personopplysninger.endreopplysninger.idporten

import io.ktor.http.Cookie
import io.ktor.http.Url
import io.ktor.server.routing.RoutingRequest

interface IDPortenService {
    fun createParams(): Triple<String, String, Pkce>
    fun createCookie(endreKontonummerState: EndreKontonummerState): Cookie
    fun createAuthorizeUrl(state: String, nonce: String, pkce: Pkce): Url
    fun createExpiredCookie(): Cookie
    fun createEndreKontonummerState(encryptedState: String?): EndreKontonummerState
    suspend fun validateRequest(request: RoutingRequest, endreKontonummerState: EndreKontonummerState, fnr: String)
    fun frontendUriWithLocale(locale: String): Url
}