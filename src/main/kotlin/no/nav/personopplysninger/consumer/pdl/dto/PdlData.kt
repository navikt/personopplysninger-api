package no.nav.personopplysninger.consumer.pdl.dto

data class PdlData(
    val person: PdlPerson?,
    val geografiskTilknytning: PdlGeografiskTilknytning?,
)