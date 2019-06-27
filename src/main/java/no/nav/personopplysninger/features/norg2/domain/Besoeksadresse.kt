package no.nav.personopplysninger.features.norg2.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Besoeksadresse (

        val type : kotlin.String? = null,
        val postnummer : kotlin.String? = null,
        val poststed : kotlin.String? = null,
        val gatenavn: kotlin.String? = null,
        val husnummer: kotlin.String? = null,
        val husbokstav: kotlin.String? = null


)