package no.nav.personopplysninger.features.personalia.model.dto

data class InnvandringUtvandring(
        val innvandretDato: String,
        val innvandretKilde: String,
        val innvandretLand: String,
        val utvandretDato: String,
        val utvandretKilde: String,
        val utvandretLand: String
)
