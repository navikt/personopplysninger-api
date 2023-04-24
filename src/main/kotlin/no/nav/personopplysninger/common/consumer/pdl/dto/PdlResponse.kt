package no.nav.personopplysninger.common.consumer.pdl.dto

import kotlinx.serialization.Serializable

@Serializable
data class PdlResponse(
    val data: PdlData,
    val extensions: PdlExtensions? = null
)