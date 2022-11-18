package no.nav.personopplysninger.common.consumer.pdl.dto.common

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.serializer.LocalDateTimeSerializer
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