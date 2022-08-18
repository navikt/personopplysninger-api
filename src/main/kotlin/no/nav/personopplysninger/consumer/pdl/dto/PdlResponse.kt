package no.nav.personopplysninger.consumer.pdl.dto

import kotlinx.serialization.Serializable

@Serializable
data class PdlResponse(
    val data: PdlData
)