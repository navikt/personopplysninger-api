package no.nav.personopplysninger.consumer.pdlmottak.domain.telefon

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException
import java.util.*

class Telefonnummer {
    @JsonProperty("@type")
    private val subtype = "TELEFONNUMMER"

    val kilde = "BRUKER SELV"
    var landskode: String? = null
        private set
    var nummer: String? = null
        private set
    var prioritet: Int? = 1
        private set

    constructor()

    constructor(landskode: String?, nummer: String?, prioritet: Int?) {
        this.landskode = landskode
        this.nummer = nummer
        this.prioritet = prioritet
    }

    @JsonCreator
    constructor(jsonProperties: Map<String, Any>) {
        this.prioritet = if (jsonProperties.containsKey("type")) {
            convertLegacyNummerType(jsonProperties["type"] as String)
        } else {
            jsonProperties["prioritet"] as Int
        }

        this.landskode = jsonProperties["landskode"] as String
        this.nummer = jsonProperties["nummer"] as String
    }

    companion object {

        // Denne var nødvendig fordi verdien på feltet 'innmeldtEndring' (som kan representere et Telefonnummer) er
        // en json representert som en string og wrappet med dobbeltfnutter.
        @JsonCreator
        @JvmStatic
        @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
        fun create(json: String): Telefonnummer {
            ObjectMapper().readValue(json, Map::class.java).let { properties ->
                return Telefonnummer(
                        landskode = properties["landskode"] as String?,
                        nummer = properties["nummer"] as String?,
                        prioritet = properties["prioritet"] as Int?
                )
            }
        }

        private fun convertLegacyNummerType(type: String): Int {
            return when (type.uppercase(Locale.getDefault())) {
                "MOBIL" -> 1
                "HJEM" -> 2
                else -> -1
            }
        }
    }
}
