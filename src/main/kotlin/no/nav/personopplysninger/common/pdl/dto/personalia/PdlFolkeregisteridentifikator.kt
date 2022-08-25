package no.nav.personopplysninger.common.pdl.dto.personalia

import kotlinx.serialization.Serializable

@Serializable
data class PdlFolkeregisteridentifikator(
    val identifikasjonsnummer: String,
    val status: String,
    val type: String,
)
