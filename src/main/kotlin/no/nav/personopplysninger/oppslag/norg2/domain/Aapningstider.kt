package no.nav.personopplysninger.oppslag.norg2.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Aapningstider (
        val id: String? = null,
        val dag: String? = null,
        val dato: String? = null,
        val stengt: String? = null,
        val fra: String? = null,
        val til: String? = null,
        val kommentar: String? = null
)
