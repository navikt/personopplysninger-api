package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamPostboksadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamUtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamVegadresse
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)

class Kontaktadresse (
        val gyldigFraOgMed: LocalDate,

        val gyldigTilOgMed: LocalDate,

        val coAdressenavn: String?,

        val adresse: Adresse
) {
        @JsonProperty("@type")
        private val subtype = "KONTAKTADRESSE"

        val kilde = "BRUKER SELV"
}