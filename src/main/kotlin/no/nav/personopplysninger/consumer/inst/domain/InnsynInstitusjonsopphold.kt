package no.nav.personopplysninger.consumer.inst.domain

import java.time.LocalDate
import java.time.LocalDateTime

data class InnsynInstitusjonsopphold (
    val organisasjonsnummer: String?,
    val institusjonsnavn: String?,
    val institusjonstype: Institusjonstype?,
    val varighet: String?,
    val kategori: String?,
    val startdato: LocalDate,
    val faktiskSluttdato: LocalDate?,
    val forventetSluttdato: LocalDate?,
    val fiktivSluttdato: Boolean?,
    val registreringstidspunkt: LocalDateTime
)
