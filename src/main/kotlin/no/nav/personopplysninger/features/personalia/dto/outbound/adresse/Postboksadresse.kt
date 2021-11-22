package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.POSTBOKSADRESSE

@JsonInclude(NON_NULL)
data class Postboksadresse (
        val postbokseier: String?,
        val postboks: String,
        val postnummer: String?,
        val poststed: String?,
): Adresse {
    override val type: AdresseType get() = POSTBOKSADRESSE
}