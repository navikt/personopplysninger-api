package no.nav.personopplysninger.common.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class PdlFolkeregistermetadata(
    @Serializable(with = LocalDateTimeSerializer::class)
    val ajourholdstidspunkt: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldighetstidspunkt: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val opphoerstidspunkt: LocalDateTime? = null,
    val kilde: String? = null,
    val aarsak: String? = null,
    val sekvens: Int? = null
)