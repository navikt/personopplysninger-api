package no.nav.personopplysninger.oppslag.norg2.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Postadresse (
        val type: String? = null,
        val postnummer: String? = null,
        val postboksnummer: String? = null,
        val postboksanlegg: String? = null,
        val poststed: String? = null,
        val gatenavn: String? = null,
        val husnummer: String? = null,
        val husbokstav: String? = null,
        val adresseTilleggsnavn: String? = null
)
