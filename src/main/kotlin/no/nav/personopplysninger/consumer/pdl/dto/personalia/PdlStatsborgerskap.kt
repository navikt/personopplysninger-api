package no.nav.personopplysninger.consumer.pdl.dto.personalia

import java.time.LocalDate

data class PdlStatsborgerskap(
    val land: String,
    val gyldigTilOgMed: LocalDate?
)