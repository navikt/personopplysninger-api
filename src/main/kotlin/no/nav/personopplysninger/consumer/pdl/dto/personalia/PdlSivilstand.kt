package no.nav.personopplysninger.consumer.pdl.dto.personalia

import java.time.LocalDate

data class PdlSivilstand(
    val type: PdlSivilstandstype,
    val gyldigFraOgMed: LocalDate?,
)