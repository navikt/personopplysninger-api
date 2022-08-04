package no.nav.personopplysninger.consumer.inst.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

enum class Institusjonstype(val tekst: String) {
    // Siden enumet brukes både i inbound og outbound, som er på forskjellige format,
    // trenger vi JsonProperty og JsonAlias for riktig serialisering og deserialisering.

    @JsonProperty("Alders- og sykehjem")
    @JsonAlias("AS")
    AS("Alders- og sykehjem"),

    @JsonProperty("Fengsel")
    @JsonAlias("FO")
    FO("Fengsel"),

    @JsonProperty("Helseinstitusjon")
    @JsonAlias("HS")
    HS("Helseinstitusjon")
}
