package no.nav.personopplysninger.features.endreopplysninger.domain.adresse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException

@JsonIgnoreProperties(ignoreUnknown = true)
class Stedsadresse {
    @JsonProperty("@type")
    private val subtype = "NORSK_STEDSADRESSE"

    val kilde = "BRUKER SELV"
    val eiendomsnavn: String? = null
    val gyldigTom: String? = null
    val kommunenummer: String? = null
    val postnummer: String? = null
    val tilleggslinje: String? = null
    val tilleggslinjeType: String? = null

    companion object {

        @JsonCreator
        @JvmStatic
        @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
        fun create(json: String): Stedsadresse {
            return ObjectMapper().readValue(json, Stedsadresse::class.java)
        }
    }
}
