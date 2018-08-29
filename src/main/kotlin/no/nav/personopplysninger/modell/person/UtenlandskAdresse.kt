package no.nav.personopplysninger.modell.person

data class UtenlandskAdresse(
        val adresse1: String,
        val adresse2: String,
        val adresse3: String,
        val datoFraOgMed: String,
        val kilde: String
)