package no.nav.personopplysninger.features.personalia.model.dto

data class Tilleggsadresse(
        val adresse1: String,
        val adresse2: String,
        val adresse3: String,
        val bolignummer: String,
        val bydel: String,
        val datoFraOgMed: String,
        val gateKode: String,
        val husbokstav: String,
        val husnummer: String,
        val kilde: String,
        val kommunenummer: String,
        val postboksanlegg: String,
        val postboksnummer: String,
        val postnummer: String
)
