package no.nav.personopplysninger.common.consumer.pdl.dto.extensions

import kotlinx.serialization.Serializable

@Serializable
data class PdlWarning(
    val message: String? = null,
    val details: PdlDetails? = null
)