package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontonummer

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException

@JsonIgnoreProperties(ignoreUnknown = true)
class Kontonummer {
    @JsonProperty("@type")
    private val subtype = "KONTONUMMER"

    val kilde = "BRUKER SELV"
    val utenlandskKontoInformasjon: UtenlandskKontoInformasjon? = null
    val value: String? = null

    companion object {

        @JsonCreator
        @JvmStatic
        @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
        fun create(json: String): Kontonummer {
            return ObjectMapper().readValue(json, Kontonummer::class.java)
        }
    }
}
