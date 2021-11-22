package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import java.time.LocalDateTime

data class Kontaktadresse (
    val gyldigFraOgMed: LocalDateTime?,
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse
)