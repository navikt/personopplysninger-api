package no.nav.personopplysninger.features.endreopplysninger.domain.telefon

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException

@JsonIgnoreProperties(ignoreUnknown = true)
class Telefonnummer {
    @JsonProperty("@type")
    private val subtype = "TELEFONNUMMER"

    var kilde = "BRUKER SELV"
    var landskode: String? = null
        private set
    var nummer: String? = null
        private set
    var type: String? = null
        private set

    constructor() {}

    constructor(kilde: String, landskode: String, nummer: String, type: String) {
        this.kilde = kilde
        this.landskode = landskode
        this.nummer = nummer
        this.type = type
    }

    constructor(landskode: String, nummer: String, type: String) {
        this.landskode = landskode
        this.nummer = nummer
        this.type = type
    }

    companion object {

        // Denne var nødvendig fordi verdien på feltet 'innmeldtEndring' (som kan representere et Telefonnummer) er
        // en json representert som en string og wrappet med dobbeltfnutter.
        @JsonCreator
        @JvmStatic
        @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
        fun create(json: String): Telefonnummer {
            return ObjectMapper().readValue(json, Telefonnummer::class.java)
        }
    }
}
