package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE

@JsonInclude(NON_NULL)
data class Vegadresse(
    val husnummer: String?,
    val husbokstav: String?,
    val bruksenhetsnummer: String?,
    val adressenavn: String?,
    val kommunenummer: String?,
    val kommune: String?,
    val tilleggsnavn: String?,
    val postnummer: String?,
    val poststed: String?
) : Adresse {
    override val type: AdresseType get() = VEGADRESSE
}

