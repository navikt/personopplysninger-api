package no.nav.personopplysninger.features.medl.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
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
