package no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse

import java.time.LocalDateTime

data class PdlFolkeregistermetadata(
        val ajourholdstidspunkt: LocalDateTime?,
        val gyldighetstidspunkt: LocalDateTime?,
        val opphoerstidspunkt: LocalDateTime?,
        val kilde: String?,
        val aarsak: String?,
        val sekvens: Int?
)