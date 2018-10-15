package no.nav.personopplysninger.features.personalia.model.dto

data class Navn(
        val datoFraOgMed: String? = null,
        val forkortetNavn: String? = null,
        val fornavn: String? = null,
        val kilde: String? = null,
        val mellomnavn: String? = null,
        val slektsnavn: String? = null,
        val slektsnavnUgift: String? = null
)
