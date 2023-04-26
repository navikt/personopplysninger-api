package no.nav.personopplysninger.common.consumer.pdl.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PdlWarning(
    val message: String? = null,
    val details: JsonElement? = null, // Kan være både objekt og string
)