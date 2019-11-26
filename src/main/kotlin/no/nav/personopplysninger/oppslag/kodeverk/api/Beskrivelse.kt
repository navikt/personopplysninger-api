package no.nav.personopplysninger.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Beskrivelse(
        @JsonProperty("term") val term: String,
        @JsonProperty("tekst") val tekst: String
)
