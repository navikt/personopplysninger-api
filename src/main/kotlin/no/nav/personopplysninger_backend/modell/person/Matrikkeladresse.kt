package no.nav.personopplysninger_backend.modell.person

data class Matrikkeladresse(
        val bruksnummer: String,
        val festenummer: String,
        val gaardsnummer: String,
        val undernummer: String
)