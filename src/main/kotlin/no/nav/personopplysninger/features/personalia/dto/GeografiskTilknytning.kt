package no.nav.personopplysninger.features.personalia.dto

data class GeografiskTilknytning(
        val bydel: String,
        val datoFraOgMed: String,
        val kilde: String,
        val kommune: String,
        val land: String
)
