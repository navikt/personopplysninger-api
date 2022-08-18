package no.nav.personopplysninger.consumer.inst.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import no.nav.personopplysninger.config.serializer.LocalDateTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class InnsynInstitusjonsopphold (
    val organisasjonsnummer: String? = null,
    val institusjonsnavn: String? = null,
    val institusjonstype: Institusjonstype? = null,
    val varighet: String? = null,
    val kategori: String? = null,
    @Serializable(with = LocalDateSerializer::class)
    val startdato: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val faktiskSluttdato: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    val forventetSluttdato: LocalDate? = null,
    val fiktivSluttdato: Boolean? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val registreringstidspunkt: LocalDateTime
)
