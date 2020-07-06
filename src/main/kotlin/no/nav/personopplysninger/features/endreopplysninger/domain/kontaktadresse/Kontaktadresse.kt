package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse;

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamPostboksadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamUtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamVegadresse
import java.time.LocalDate

class Kontaktadresse (
        @JsonProperty("@type")
        val type: String = "KONTAKTADRESSE",

        val kilde: String = "BRUKER SELV",

        val gyldigFraOgMed: LocalDate,

        val gyldigTilOgMed: LocalDate,

        val coAdressenavn: String?,

        val adresse: Adresse
)