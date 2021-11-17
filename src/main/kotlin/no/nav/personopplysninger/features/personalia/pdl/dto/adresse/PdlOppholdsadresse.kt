package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

import java.time.LocalDateTime

data class PdlOppholdsadresse(
    val gyldigFraOgMed: LocalDateTime?,
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val utenlandskAdresse: PdlUtenlandskAdresse?,
    val vegadresse: PdlVegadresse?,
    val matrikkeladresse: PdlMatrikkeladresse?,
    val oppholdAnnetSted: String?,
)