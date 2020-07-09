package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import com.fasterxml.jackson.annotation.JsonProperty

class Vegadresse (
        @JsonProperty("@type")
        val typeAdresse: String = AdresseType.VEGADRESSE.name,
        val matrikkelId: String? = null,
        val bruksenhetsnummer: String? = null,
        val adressenavn: String? = null,
        val husnummer: String? = null,
        val husbokstav: String? = null,
        val tilleggsnavn: String? = null,
        val postnummer: String? = null
): Adresse()