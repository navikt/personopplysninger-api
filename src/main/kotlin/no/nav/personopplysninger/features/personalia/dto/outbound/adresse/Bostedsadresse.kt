package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import no.nav.personopplysninger.config.serializer.LocalDateTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class Bostedsadresse(
    @Serializable(with = LocalDateSerializer::class)
    val angittFlyttedato: LocalDate? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigFraOgMed: LocalDateTime?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse
)