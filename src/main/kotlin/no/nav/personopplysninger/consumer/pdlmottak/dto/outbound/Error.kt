package no.nav.personopplysninger.consumer.pdlmottak.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val message: String? = null,
    val details: Map<String, List<String>>? = null
)
