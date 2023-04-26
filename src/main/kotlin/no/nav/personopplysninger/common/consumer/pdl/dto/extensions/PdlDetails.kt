package no.nav.personopplysninger.common.consumer.pdl.dto.extensions

import kotlinx.serialization.Serializable

@Serializable
data class PdlDetails(
    val missing: List<String> = emptyList(),
)