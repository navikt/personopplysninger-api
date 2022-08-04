package no.nav.personopplysninger.consumer.pdlmottak.dto.kontaktadresse

import com.fasterxml.jackson.annotation.JsonProperty

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