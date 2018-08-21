package no.nav.personopplysninger_backend.modell.person

data class Statsborgerskap(
        val datoFraOgMed: String,
        val kilde: String,
        val kode: String
)