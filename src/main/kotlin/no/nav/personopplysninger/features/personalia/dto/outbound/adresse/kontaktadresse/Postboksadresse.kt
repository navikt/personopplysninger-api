package no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

@JsonInclude(NON_NULL)
data class Postboksadresse (
        val postbokseier: String?,
        val postboks: String,
        val postnummer: String?,
        val poststed: String?,
        val gyldigFraOgMed: String?,
        val gyldigTilOgMed: String?
)