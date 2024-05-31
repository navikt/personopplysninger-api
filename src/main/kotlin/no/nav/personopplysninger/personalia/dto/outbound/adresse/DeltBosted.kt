package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable
import no.nav.pdl.generated.dto.Date

@Serializable
data class DeltBosted(
    val startdatoForKontrakt: Date,
    val sluttdatoForKontrakt: Date? = null,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse
)