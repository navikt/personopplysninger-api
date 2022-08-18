package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class DeltBosted(
    @Serializable(with = LocalDateSerializer::class)
    val startdatoForKontrakt: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    val sluttdatoForKontrakt: LocalDate? = null,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse
)