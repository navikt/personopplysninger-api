package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

data class PdlPostboksadresse (
    val postbokseier: String?,
    val postboks: String,
    val postnummer: String?
)