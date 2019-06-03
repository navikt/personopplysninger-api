package no.nav.personopplysninger.features.norg2.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Norg2EnhetKontaktinfo (

        val id : kotlin.String? = null,
        val telefonnummer : kotlin.String? = null,
        val faksnummer : kotlin.String? = null,
        val postadresse : Postadresse? = null,
        val besoeksadresse : Besoeksadresse? = null,
        val spesielleOpplysninger: kotlin.String? = null,
        val publikumsmottak : List<Publikumsmottak>? = null,
        val aapningstider : List<Aapningstider>? = null,
        val stedsbeskrivelse : kotlin.String? = null

)