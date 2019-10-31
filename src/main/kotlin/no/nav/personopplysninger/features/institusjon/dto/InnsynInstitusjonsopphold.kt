package no.nav.personopplysninger.features.institusjon.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class InnsynInstitusjonsopphold (
        val organisasjonsnummer: String,
        val institusjonsnavn: String?,
        val institusjonstype: String,
        val varighet: String,
        val kategori: String,
        val startdato: LocalDate,
        val faktiskSluttdato: LocalDate?,
        val forventetSluttdato: LocalDate?,
        val registreringstidspunkt: LocalDateTime
)
