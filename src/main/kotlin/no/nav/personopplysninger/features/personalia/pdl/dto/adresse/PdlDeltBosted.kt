package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

import java.time.LocalDate

data class PdlDeltBosted(
    val startdatoForKontrakt: LocalDate?,
    val sluttdatoForKontrakt: LocalDate?,
    val coAdressenavn: String?,
    val vegadresse: PdlVegadresse?,
    val matrikkeladresse: PdlMatrikkeladresse?,
    val utenlandskAdresse: PdlUtenlandskAdresse?,
    val ukjentBosted: PdlUkjentbosted?,
)