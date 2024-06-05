package no.nav.personopplysninger.kontaktinformasjon.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kontaktinformasjon(
    val epostadresse: String? = null,
    val mobiltelefonnummer: String? = null,
    val reservert: Boolean? = null,
    val spraak: String? = null
)
