package no.nav.personopplysninger.features.personaliagammel.dto.outbound

data class Kontaktinformasjon(
        val epostadresse: String? = null,
        val kanVarsles: Boolean? = null,
        val mobiltelefonnummer: String? = null,
        val reservert: Boolean? = null
)
