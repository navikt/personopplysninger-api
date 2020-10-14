package no.nav.personopplysninger.features.medl.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Studieinformasjon (
        val statsborgerland: String,
        val studieland: String
)
