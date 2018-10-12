package no.nav.personopplysninger.features.person.model.dto

data class Tiltak(
        val datoFraOgMed: String,
        val datoTil: String,
        val kilde: String,
        val type: String
)
