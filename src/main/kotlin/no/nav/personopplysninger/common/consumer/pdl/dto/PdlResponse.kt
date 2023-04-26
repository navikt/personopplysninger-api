package no.nav.personopplysninger.common.consumer.pdl.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.consumer.pdl.dto.extensions.PdlExtensions

@Serializable
data class PdlResponse(
    val data: PdlData,
    val extensions: PdlExtensions? = null
)