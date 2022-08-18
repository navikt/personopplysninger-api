package no.nav.personopplysninger.consumer.kontoregister.dto

import kotlinx.serialization.Serializable

@Serializable
data class Konto(
    val kontonummer: String,
    val utenlandskKontoInfo: UtenlandskKontoInfo? = null
)
