package no.nav.personopplysninger.features.institusjon.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDate
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class InnsynInstitusjonsopphold (
        val organisasjonsnummer: String?,
        val institusjonsnavn: String?,
        @JsonSerialize(using = InstitusjonstypeSerializer::class) val institusjonstype: Institusjonstype?,
        val varighet: String?,
        val kategori: String?,
        val startdato: LocalDate,
        val faktiskSluttdato: LocalDate?,
        val forventetSluttdato: LocalDate?,
        val fiktivSluttdato: Boolean?,
        val registreringstidspunkt: LocalDateTime
)
