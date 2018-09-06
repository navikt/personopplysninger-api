package no.nav.personopplysninger.modell.person

data class BrukerbehovListe(
        val behov: String,
        val datoFraOgMed: String,
        val kilde: String
)