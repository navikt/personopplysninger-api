package no.nav.personopplysninger.features.person.model.dto

data class Egenansatt(
        val datoFraOgMed: String,
        val erEgenansatt: Boolean,
        val kilde: String
)
