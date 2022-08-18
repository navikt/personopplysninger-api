package no.nav.personopplysninger.consumer.kontaktinformasjon.dto

import kotlinx.serialization.Serializable

@Serializable
data class DigitalKontaktinformasjon (
    val personident: String? = null,
    val aktiv: Boolean,
    val kanVarsles: Boolean? = null,
    val reservert: Boolean? = null,
    val spraak: String? = null,
    val epostadresse: String? = null,
    val mobiltelefonnummer: String? = null,
    val sikkerDigitalPostkasse: SikkerDigitalPostkasse? = null,
)