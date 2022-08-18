package no.nav.personopplysninger.consumer.pdl.dto.personalia

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

import kotlinx.serialization.Serializable

@Serializable
data class PdlTelefonnummer(
    val landskode: String,
    val nummer: String,
    val prioritet: Int,
    val opplysningsId: String
) {
    @JsonCreator
    constructor(
        @JsonProperty("landskode") landskode: String,
        @JsonProperty("nummer") nummer: String,
        @JsonProperty("prioritet") prioritet: Int,
        @JsonProperty("metadata") metadata: Map<String, Any>
    ) : this(landskode, nummer, prioritet, metadata["opplysningsId"] as String)

}