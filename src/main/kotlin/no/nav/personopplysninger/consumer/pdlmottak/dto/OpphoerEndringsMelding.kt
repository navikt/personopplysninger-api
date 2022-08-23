package no.nav.personopplysninger.consumer.pdlmottak.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OpphoerEndringsMelding (
    @JsonProperty("@type")
    override val subtype: String = "OPPHOER",
    override val kilde: String = "BRUKER SELV",
): Endringsmelding