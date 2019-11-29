package no.nav.personopplysninger.features.auth

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Navn (
        val kortNavn: String? = null,
        val fornavn: String? = null,
        val etternavn: String? = null
)
