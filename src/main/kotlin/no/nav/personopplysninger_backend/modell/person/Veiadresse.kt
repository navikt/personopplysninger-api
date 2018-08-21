package no.nav.personopplysninger_backend.modell.person

data class Veiadresse(
        val bokstav: String,
        val bolignummer: String,
        val gatekode: String,
        val husnummer: String
)