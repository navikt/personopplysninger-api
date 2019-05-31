package no.nav.personopplysninger.features.norg2.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Aapningstider (

        val id : kotlin.String? = null,
        val dag : kotlin.String? = null,
        val dato : kotlin.String? = null,
        val stengt : kotlin.String? = null,
        val fra : kotlin.String? = null,
        val til : kotlin.String? = null
)