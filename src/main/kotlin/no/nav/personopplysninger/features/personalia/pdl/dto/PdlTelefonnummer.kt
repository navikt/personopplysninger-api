package no.nav.personopplysninger.features.personalia.pdl.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class PdlTelefonnummer (
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