package no.nav.personopplysninger.consumer.norg2.domain

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