package no.nav.personopplysninger.consumer.pdl.dto.adresse

data class PdlPostboksadresse (
    val postbokseier: String?,
    val postboks: String,
    val postnummer: String?
)