package no.nav.personopplysninger.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RetningsnummerDTO(
        val landskode: String,
        val land: String
)
