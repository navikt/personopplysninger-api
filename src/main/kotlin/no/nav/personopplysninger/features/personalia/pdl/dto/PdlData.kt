package no.nav.personopplysninger.features.personalia.pdl.dto

data class PdlData(
    val person: PdlPerson?,
    val geografiskTilknytning: PdlGeografiskTilknytning?,
    val identer: PdlIdentliste?
)