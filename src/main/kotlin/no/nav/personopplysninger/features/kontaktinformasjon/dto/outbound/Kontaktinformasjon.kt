package no.nav.personopplysninger.features.kontaktinformasjon.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Kontaktinformasjon(
    val epostadresse: String? = null,
    val kanVarsles: Boolean? = null,
    val mobiltelefonnummer: String? = null,
    val reservert: Boolean? = null,
    val spraak: String? = null
)
