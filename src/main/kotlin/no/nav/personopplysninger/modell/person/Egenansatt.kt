package no.nav.personopplysninger.modell.person

data class Egenansatt(
        val datoFraOgMed: String,
        val erEgenansatt: Boolean,
        val kilde: String
)