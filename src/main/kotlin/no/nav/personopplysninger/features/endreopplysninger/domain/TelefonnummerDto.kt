package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TelefonnummerDto (
        val kilde : kotlin.String,
        val landskode : kotlin.String? = null,
        val nummer : kotlin.Int,
        val type: kotlin.String
)