package no.nav.personopplysninger.modell.person

data class VergemaalListe(
        val aaksId: String,
        val datoFraOgMed: String,
        val egenansatt: Boolean,
        val embete: String,
        val fnr: String,
        val id: String,
        val kilde: String,
        val mandattype: String,
        val sakstype: String,
        val spesreg: String,
        val type: String,
        val vedtaksdato: String
)