package no.nav.personopplysninger.features.personalia.dto.outbound

data class Kontaktinformasjon(
        val epostadresse: String? = null,
        val kanVarsles: Boolean? = null,
        val mobiltelefonnummer: String? = null,
        val reservert: Boolean? = null
)
