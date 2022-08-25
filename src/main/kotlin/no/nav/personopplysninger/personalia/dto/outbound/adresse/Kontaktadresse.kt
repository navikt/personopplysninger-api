package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Kontaktadresse(
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigFraOgMed: LocalDateTime?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse
)