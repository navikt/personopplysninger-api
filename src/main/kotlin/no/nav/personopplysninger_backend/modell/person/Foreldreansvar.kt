package no.nav.personopplysninger_backend.modell.person

data class Foreldreansvar(
        val datoFraOgMed: String,
        val kilde: String,
        val type: String
)