package no.nav.personopplysninger_backend.modell.person

data class Tiltak(
        val datoFraOgMed: String,
        val datoTil: String,
        val kilde: String,
        val type: String
)