package no.nav.personopplysninger.consumer.pdlmottak.dto.opphoer

import com.fasterxml.jackson.annotation.JsonProperty

class OpphoerEndringsMelding private constructor() {
    @JsonProperty("@type")
    val subtype = "OPPHOER"
    val kilde = "BRUKER SELV"

    companion object {
        val opphoerEndringsMelding = OpphoerEndringsMelding()
    }
}