package no.nav.personopplysninger.oppslag.kodeverk.api

import java.time.LocalDate

data class Betydning (
        val gyldigFra: LocalDate,
        val gyldigTil: LocalDate,
        val beskrivelser: Map<String, Beskrivelse>
)
