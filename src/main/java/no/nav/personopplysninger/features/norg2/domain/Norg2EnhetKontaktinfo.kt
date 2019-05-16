package no.nav.personopplysninger.features.norg2.domain


data class Norg2EnhetKontaktinfo (

        val id : kotlin.String? = null,
        val telefonnummer : kotlin.String? = null,
        val faksnummer : kotlin.String? = null,
        val postadresse : Postadresse? = null,
        val besoeksadresse : Besoeksadresse? = null,
        val spesielleOpplysninger: kotlin.String? = null,
        val publikumsmottak : Publikumsmottak? = null,
        val aapningstider : Aapningstider? = null,
        val stedsbeskrivelse : kotlin.String? = null

)