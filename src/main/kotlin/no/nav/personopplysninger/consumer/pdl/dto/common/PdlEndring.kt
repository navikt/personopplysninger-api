package no.nav.personopplysninger.consumer.pdl.dto.common

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class PdlEndring(
    val type: PdlEndringstype,
    @Serializable(with = LocalDateTimeSerializer::class)
    val registrert: LocalDateTime,
    val registrertAv: String,
    val systemkilde: String,
    val kilde: String
)