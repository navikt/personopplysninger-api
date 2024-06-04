package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable

@Serializable
data class DeltBosted(
    val coAdressenavn: String?,
    val adresse: Adresse
)