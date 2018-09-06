package no.nav.personopplysninger.modell.person

data class Relasjon(
        val datoFraOgMed: String,
        val egenansatt: Boolean,
        val fnr: String,
        val kilde: String,
        val spesreg: String,
        val type: String
)