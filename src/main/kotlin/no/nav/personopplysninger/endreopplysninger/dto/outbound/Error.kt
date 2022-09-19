package no.nav.personopplysninger.endreopplysninger.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    var message: String? = null,
    val details: Map<String, List<String>>? = null
)
