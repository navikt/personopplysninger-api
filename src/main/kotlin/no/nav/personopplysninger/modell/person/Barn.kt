package no.nav.personopplysninger.modell.person

data class Barn(
        val datoRegistert: String = "",
        val egenansatt: Boolean = false,
        val fnr: String = "",
        val kilde: String = "",
        val spesreg: String = ""
)