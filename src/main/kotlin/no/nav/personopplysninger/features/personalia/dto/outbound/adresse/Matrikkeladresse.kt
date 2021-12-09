package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE

data class Matrikkeladresse(
    val bruksenhetsnummer: String?,
    val tilleggsnavn: String?,
    val postnummer: String?,
    val poststed: String?,
    val kommunenummer: String?,
    val kommune: String?,
) : Adresse {
    override val type: AdresseType get() = MATRIKKELADRESSE
}