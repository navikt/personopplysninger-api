package no.nav.personopplysninger.features.person.model.dto

data class Barn(
        val datoRegistert: String = "",
        val egenansatt: Boolean = false,
        val fnr: String = "",
        val kilde: String = "",
        val spesreg: String = ""
)
