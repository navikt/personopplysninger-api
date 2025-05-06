package no.nav.personopplysninger.endreopplysninger.idporten

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Kontonummer

@Serializable
data class EndreKontonummerState(
    val state: String,
    val nonce: String,
    val codeVerifier: String,
    val kontonummer: Kontonummer,
    val bruker: String,
    val locale: String
)