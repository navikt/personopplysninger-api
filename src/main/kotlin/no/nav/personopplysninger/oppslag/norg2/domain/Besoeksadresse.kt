package no.nav.personopplysninger.oppslag.norg2.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Besoeksadresse (
        val type: String? = null,
        val postnummer: String? = null,
        val poststed: String? = null,
        val gatenavn: String? = null,
        val husnummer: String? = null,
        val husbokstav: String? = null
)
