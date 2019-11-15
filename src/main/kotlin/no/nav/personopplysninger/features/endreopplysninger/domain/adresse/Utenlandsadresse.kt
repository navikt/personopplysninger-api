package no.nav.personopplysninger.features.endreopplysninger.domain.adresse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException

@JsonIgnoreProperties(ignoreUnknown = true)
class Utenlandsadresse {
    @JsonProperty("@type")
    private val subtype = "UTENLANDSK_KONTAKTADRESSE"

    val kilde = "BRUKER SELV"
    val adresselinje1: String? = null
    val adresselinje2: String? = null
    val adresselinje3: String? = null
    val gyldigTom: String? = null
    val landkode: String? = null

    companion object {

        @JsonCreator
        @JvmStatic
        @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
        fun create(json: String): Utenlandsadresse {
            return ObjectMapper().readValue(json, Utenlandsadresse::class.java)
        }
    }
}
