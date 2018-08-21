package no.nav.personopplysninger_backend.modell.person

data class Oppholdstillatelse(
        val datoFraOgMed: String,
        val kilde: String,
        val type: String
)