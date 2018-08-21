package no.nav.personopplysninger_backend.modell.person

data class Postadresse(
        val adresse1: String,
        val adresse2: String,
        val adresse3: String,
        val datoFraOgMed: String,
        val kilde: String,
        val land: String,
        val postnummer: String
)