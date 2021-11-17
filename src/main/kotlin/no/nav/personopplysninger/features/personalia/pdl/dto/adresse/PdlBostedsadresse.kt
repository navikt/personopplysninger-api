package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

import java.time.LocalDate
import java.time.LocalDateTime

data class PdlBostedsadresse(
    val angittFlyttedato: LocalDate?,
    val gyldigFraOgMed: LocalDateTime?,
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val vegadresse: PdlVegadresse?,
    val matrikkeladresse: PdlMatrikkeladresse?,
    val utenlandskAdresse: PdlUtenlandskAdresse?,
    val ukjentBosted: PdlUkjentbosted?,
)