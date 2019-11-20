package no.nav.personopplysninger.features.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KodeOgTekstDto(
        @JsonProperty("kode") val kode: String,
        @JsonProperty("tekst") val tekst: String
)
