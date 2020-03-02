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

    val kilde = "BRUKER SELV"
    var landskode: String? = null
        private set
    var nummer: String? = null
        private set
    var prioritet: Int = 1
        private set

    constructor() {}

    constructor(landskode: String, nummer: String, prioritet: Int) {
        this.landskode = landskode
        this.nummer = nummer
        this.prioritet = prioritet
    }

    @JsonCreator
    constructor(
            @JsonProperty("type") type: String,
            @JsonProperty("landskode") landskode: String,
            @JsonProperty("nummer") nummer: String
    ): this (landskode, nummer, convertLegacyNummerType(type))

    companion object {

        // Denne var nødvendig fordi verdien på feltet 'innmeldtEndring' (som kan representere et Telefonnummer) er
        // en json representert som en string og wrappet med dobbeltfnutter.
        @JsonCreator
        @JvmStatic
        @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
        fun create(json: String): Telefonnummer {
            ObjectMapper().readValue(json, Map::class.java).let { properties ->
                return Telefonnummer(
                        landskode = properties["landskode"] as String,
                        nummer = properties["nummer"] as String,
                        prioritet = properties["prioritet"] as Int
                )
            }
        }

        private fun convertLegacyNummerType(type: String): Int {
            return when (type.toUpperCase()) {
                "MOBIL" -> 1
                "HJEM" -> 2
                else -> -1
            }
        }
    }
}
