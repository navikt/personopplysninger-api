package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.AdresseType.POSTBOKSADRESSE

class Postboksadresse(
        @JsonProperty("@type")
        val typeAdresse: AdresseType = POSTBOKSADRESSE,
        val postbokseier: String? = null,
        val postboks: String,
        val postnummer: String
) : Adresse()