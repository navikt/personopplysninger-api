package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TelefonnummerDto (

        val kilde : kotlin.String? = null,
        val landskode : kotlin.String? = null,
        val nummer : kotlin.Int? = null,
        val type: kotlin.String? = null
)