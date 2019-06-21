package no.nav.personopplysninger.features.norg2.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Publikumsmottak (

        val id : kotlin.String? = null,
        val besoeksadresse : Besoeksadresse? = null,
        val aapningstider : List<Aapningstider>? = null,
        val stedsbeskrivelse : String? = null
)