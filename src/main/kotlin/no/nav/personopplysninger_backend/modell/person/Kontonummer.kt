package no.nav.personopplysninger_backend.modell.person

data class Kontonummer(
        val datoFraOgMed: String,
        val kilde: String,
        val nummer: String
)