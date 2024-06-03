package no.nav.personopplysninger.consumer.kodeverk.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class Betydning(
    @Serializable(with = LocalDateSerializer::class)
    val gyldigFra: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val gyldigTil: LocalDate,
    val beskrivelser: Map<String, Beskrivelse>
)
