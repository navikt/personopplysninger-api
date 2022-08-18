package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import java.time.LocalDate

data class DeltBosted(
    val startdatoForKontrakt: LocalDate? = null,
    val sluttdatoForKontrakt: LocalDate? = null,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse
)