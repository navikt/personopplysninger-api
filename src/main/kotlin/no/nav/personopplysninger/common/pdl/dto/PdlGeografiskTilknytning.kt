package no.nav.personopplysninger.common.pdl.dto

import kotlinx.serialization.Serializable

@Serializable
data class PdlGeografiskTilknytning(
    val gtKommune: String? = null,
    val gtBydel: String? = null,
    val gtLand: String? = null,
)