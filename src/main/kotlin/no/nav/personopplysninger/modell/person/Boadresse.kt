package no.nav.personopplysninger.modell.person

data class Boadresse(
        val adresse: String,
        val adressetillegg: String,
        val bydel: String,
        val datoFraOgMed: String,
        val kilde: String,
        val kommune: String,
        val landkode: String,
        val matrikkeladresse: Matrikkeladresse,
        val postnummer: String,
        val veiadresse: Veiadresse
)