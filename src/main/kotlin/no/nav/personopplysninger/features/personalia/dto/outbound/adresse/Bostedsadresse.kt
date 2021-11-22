package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import java.time.LocalDate
import java.time.LocalDateTime

data class Bostedsadresse (
    val angittFlyttedato: LocalDate?,
    val gyldigFraOgMed: LocalDateTime?,
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val adresse: Adresse
)