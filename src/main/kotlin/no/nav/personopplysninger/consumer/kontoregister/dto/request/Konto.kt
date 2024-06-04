package no.nav.personopplysninger.consumer.kontoregister.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class Konto(
    val kontonummer: String? = null,
    val utenlandskKontoInfo: UtenlandskKontoInfo? = null,
    val error: Boolean = false
)
