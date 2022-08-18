package no.nav.personopplysninger.consumer.pdl.dto.personalia

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.consumer.pdl.dto.common.PdlMetadata

@Serializable
data class PdlTelefonnummer(
    val landskode: String,
    val nummer: String,
    val prioritet: Int,
    val metadata: PdlMetadata
)