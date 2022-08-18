package no.nav.personopplysninger.consumer.pdl.dto.personalia

import kotlinx.serialization.Serializable

@Serializable
data class PdlFoedsel(
    val foedested: String? = null,
    val foedekommune: String? = null,
    val foedeland: String? = null,
)