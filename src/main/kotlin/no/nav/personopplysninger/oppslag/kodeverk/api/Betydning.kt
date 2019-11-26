package no.nav.personopplysninger.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Betydning (
        @JsonProperty("gyldigFra") @JsonDeserialize(using = LocalDateDeserializer::class) val gyldigFra: LocalDate,
        @JsonProperty("gyldigTil") @JsonDeserialize(using = LocalDateDeserializer::class) val gyldigTil: LocalDate,
        @JsonProperty("beskrivelser") val beskrivelser: Map<String, Beskrivelse>
)
