package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import com.fasterxml.jackson.annotation.JsonProperty

abstract class Adresse (
        @JsonProperty("@type")
        val typeAdresse: AdresseType
)

enum class AdresseType {
    UTENLANDSK_ADRESSE, POSTBOKSADRESSE, VEGADRESSE
}