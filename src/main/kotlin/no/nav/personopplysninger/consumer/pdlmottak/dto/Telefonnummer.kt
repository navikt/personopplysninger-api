package no.nav.personopplysninger.consumer.pdlmottak.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Telefonnummer(
    @JsonProperty("@type")
    override val subtype: String = "TELEFONNUMMER",
    override val kilde: String = "BRUKER SELV",
    val landskode: String? = null,
    val nummer: String? = null,
    val prioritet: Int? = 1,
): Endringsmelding