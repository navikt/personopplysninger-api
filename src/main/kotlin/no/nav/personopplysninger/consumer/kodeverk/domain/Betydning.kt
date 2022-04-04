package no.nav.personopplysninger.consumer.kodeverk.domain

import java.time.LocalDate

data class Betydning (
        val gyldigFra: LocalDate,
        val gyldigTil: LocalDate,
        val beskrivelser: Map<String, Beskrivelse>
)
