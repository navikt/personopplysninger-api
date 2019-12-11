package no.nav.personopplysninger.features.medl.domain

import java.time.LocalDate
import java.time.LocalDateTime

data class Sporingsinformasjon (
        val besluttet: LocalDate?,
        val kilde: String,
        val kildedokument: String?,
        val opprettet: LocalDateTime,
        val opprettetAv: String,
        val registrert: LocalDate?,
        val sistEndret: LocalDateTime,
        val sistEndretAv: String,
        val versjon: Int
)
