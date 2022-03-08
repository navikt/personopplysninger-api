package no.nav.personopplysninger.features.personalia.kontaktinformasjon

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