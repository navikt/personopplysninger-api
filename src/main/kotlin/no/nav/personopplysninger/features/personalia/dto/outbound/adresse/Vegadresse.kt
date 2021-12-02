package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE

@JsonInclude(NON_NULL)
data class Vegadresse (
        val husnummer: String?,
        val husbokstav: String?,
        val bruksenhetsnummer: String?,
        val adressenavn: String?,
        val kommunenummer: String?,
        val tilleggsnavn: String?,
        val postnummer: String?,
        val poststed: String?
        override val kilde: String?
): Kontaktadresse {
    override val type: KontaktadresseType get() = VEGADRESSE
}

