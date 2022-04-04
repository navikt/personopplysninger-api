package no.nav.personopplysninger.consumer.pdl.dto.adresse

data class PdlMatrikkeladresse (
    val bruksenhetsnummer: String?,
    val tilleggsnavn: String?,
    val postnummer: String?,
    val kommunenummer: String?
)
