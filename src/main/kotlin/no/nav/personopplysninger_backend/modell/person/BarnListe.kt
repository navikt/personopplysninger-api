package no.nav.personopplysninger_backend.modell.person

data class BarnListe(
        val datoRegistert: String,
        val egenansatt: Boolean,
        val fnr: String,
        val kilde: String,
        val spesreg: String
)