package no.nav.personopplysninger.common.consumer.pdl.dto.common

import kotlinx.serialization.Serializable

@Serializable
data class PdlMetadata(
    val opplysningsId: String? = null,
    val master: String? = null,
    val endringer: List<PdlEndring>? = emptyList(),
    val historisk: Boolean? = null
)