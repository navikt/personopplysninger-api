package no.nav.personopplysninger.config

import io.ktor.http.Url
import javax.crypto.SecretKey

class IDPortenConfig(
    val redirectUri: String,
    val frontendUri: Url,
    val wellKnownUrl: String,
    val clientId: String,
    val clientJwk: String,
    val encryptionKey: SecretKey,
    val acr: String = "idporten-loa-high",
    val allowedAuthTimeSkewSeconds: Long = 5,
    val secureCookie: Boolean = true,
)