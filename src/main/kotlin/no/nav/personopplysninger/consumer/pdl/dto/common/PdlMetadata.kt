package no.nav.personopplysninger.consumer.pdl.dto.common

import kotlinx.serialization.Serializable

@Serializable
data class PdlMetadata(
    val opplysningsId: String? = null,
    val master: String,
    val endringer: List<PdlEndring>,
    val historisk: Boolean
)