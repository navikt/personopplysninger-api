package no.nav.personopplysninger_backend.modell.person

data class Navn(
        val datoFraOgMed: String,
        val forkortetNavn: String,
        val fornavn: String,
        val kilde: String,
        val mellomnavn: String,
        val slektsnavn: String,
        val slektsnavnUgift: String
)