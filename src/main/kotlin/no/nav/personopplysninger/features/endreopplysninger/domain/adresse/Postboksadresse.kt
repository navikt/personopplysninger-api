package no.nav.personopplysninger.features.endreopplysninger.domain.adresse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException

@JsonIgnoreProperties(ignoreUnknown = true)
class Postboksadresse {
    @JsonProperty("@type")
    private val subtype = "NORSK_POSTBOKSADRESSE"

    val kilde = "BRUKER SELV"
    val gyldigTom: String? = null
    val kommunenummer: String? = null
    val postboksnummer: String? = null
    val postnummer: String? = null
    val tilleggslinje: String? = null
    val tilleggslinjeType: String? = null
    val postboksanlegg: String? = null

    companion object {

        @JsonCreator
        @JvmStatic
        @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
        fun create(json: String): Postboksadresse {
            return ObjectMapper().readValue(json, Postboksadresse::class.java)
        }
    }
}
