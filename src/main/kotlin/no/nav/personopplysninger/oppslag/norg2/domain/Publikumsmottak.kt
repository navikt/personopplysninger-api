package no.nav.personopplysninger.oppslag.norg2.domain

data class Publikumsmottak (
        val id: String? = null,
        val besoeksadresse: Besoeksadresse? = null,
        val aapningstider: List<Aapningstider>? = null,
        val stedsbeskrivelse: String? = null
)
