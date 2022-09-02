package no.nav.personopplysninger.common.pdl.dto

import kotlinx.serialization.Serializable

@Serializable
data class PdlData(
    val person: PdlPerson? = null,
    val geografiskTilknytning: PdlGeografiskTilknytning? = null,
)