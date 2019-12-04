package no.nav.personopplysninger.features.institusjon.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDate
import java.time.LocalDateTime

data class InnsynInstitusjonsopphold (
        @JsonProperty("organisasjonsnummer") val organisasjonsnummer: String,
        @JsonProperty("institusjonsnavn") val institusjonsnavn: String?,
        @JsonProperty("institusjonstype") @JsonSerialize(using = InstitusjonstypeSerializer::class) val institusjonstype: Institusjonstype,
        @JsonProperty("varighet") val varighet: String?,
        @JsonProperty("kategori") val kategori: String?,
        @JsonProperty("startdato") @JsonDeserialize(using = LocalDateDeserializer::class) val startdato: LocalDate,
        @JsonProperty("faktiskSluttdato") @JsonDeserialize(using = LocalDateDeserializer::class) val faktiskSluttdato: LocalDate?,
        @JsonProperty("forventetSluttdato") @JsonDeserialize(using = LocalDateDeserializer::class) val forventetSluttdato: LocalDate?,
        @JsonProperty("registreringstidspunkt") @JsonDeserialize(using = LocalDateTimeDeserializer::class) val registreringstidspunkt: LocalDateTime
)
