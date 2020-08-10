package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)

class Kontaktadresse (
        val gyldigFraOgMed: String,

        val gyldigTilOgMed: String,

        val coAdressenavn: String?,

        val adresse: Adresse
) {
        @JsonProperty("@type")
        private val subtype = "KONTAKTADRESSE"

        val kilde = "BRUKER SELV"
}