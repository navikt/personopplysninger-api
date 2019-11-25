package no.nav.personopplysninger.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RetningsnummerDTO(
        @JsonProperty("landskode") val landskode: String,
        @JsonProperty("land") val land: String
)
