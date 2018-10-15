package no.nav.personopplysninger.features.personalia.model.dto

data class Tiltak(
        val datoFraOgMed: String,
        val datoTil: String,
        val kilde: String,
        val type: String
)
