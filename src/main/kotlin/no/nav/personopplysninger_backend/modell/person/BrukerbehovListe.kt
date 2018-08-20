package no.nav.personopplysninger_backend.modell.person

data class BrukerbehovListe(
        val behov: String,
        val datoFraOgMed: String,
        val kilde: String
)