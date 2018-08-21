package no.nav.personopplysninger_backend.modell.person

data class InnvandringUtvandring(
        val innvandretDato: String,
        val innvandretKilde: String,
        val innvandretLand: String,
        val utvandretDato: String,
        val utvandretKilde: String,
        val utvandretLand: String
)