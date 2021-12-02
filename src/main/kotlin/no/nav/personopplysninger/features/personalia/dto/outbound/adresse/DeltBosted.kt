package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import java.time.LocalDate

data class DeltBosted(
    val startdatoForKontrakt: LocalDate?,
    val sluttdatoForKontrakt: LocalDate?,
    val coAdressenavn: String?,
    val adresse: Adresse
)