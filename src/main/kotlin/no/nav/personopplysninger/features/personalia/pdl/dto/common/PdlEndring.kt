package no.nav.personopplysninger.features.personalia.pdl.dto.common

import java.time.LocalDateTime

data class PdlEndring (
        val type: PdlEndringstype,
        val registrert: LocalDateTime,
        val registrertAv: String,
        val systemkilde: String,
        val kilde: String
)