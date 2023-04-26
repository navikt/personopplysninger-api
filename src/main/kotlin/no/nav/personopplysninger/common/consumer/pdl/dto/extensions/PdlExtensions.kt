package no.nav.personopplysninger.common.consumer.pdl.dto.extensions

import kotlinx.serialization.Serializable

@Serializable
data class PdlExtensions(
    val warnings: List<PdlWarning>? = null,
)