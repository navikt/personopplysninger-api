package no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse

data class PdlPostboksadresse (
    val postbokseier: String?,
    val postboks: String,
    val postnummer: String?
)