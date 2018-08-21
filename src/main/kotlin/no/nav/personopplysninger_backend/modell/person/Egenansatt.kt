package no.nav.personopplysninger_backend.modell.person

data class Egenansatt(
        val datoFraOgMed: String,
        val erEgenansatt: Boolean,
        val kilde: String
)