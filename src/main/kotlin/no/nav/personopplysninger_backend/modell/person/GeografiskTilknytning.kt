package no.nav.personopplysninger_backend.modell.person

data class GeografiskTilknytning(
        val bydel: String,
        val datoFraOgMed: String,
        val kilde: String,
        val kommune: String,
        val land: String
)