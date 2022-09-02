package no.nav.personopplysninger.common.pdl.dto.personalia

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.pdl.dto.common.PdlMetadata

@Serializable
data class PdlTelefonnummer(
    val landskode: String,
    val nummer: String,
    val prioritet: Int,
    val metadata: PdlMetadata
)