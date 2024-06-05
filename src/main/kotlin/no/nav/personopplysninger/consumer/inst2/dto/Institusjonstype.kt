package no.nav.personopplysninger.consumer.inst2.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
enum class Institusjonstype(val tekst: String) {

    // Siden enumet brukes både i inbound og outbound, som er på forskjellige format,
    // trenger vi SerialName og JsonNames for riktig serialisering og deserialisering.

    @SerialName("Alders- og sykehjem")
    @JsonNames("AS")
    AS("Alders- og sykehjem"),

    @SerialName("Fengsel")
    @JsonNames("FO")
    FO("Fengsel"),

    @SerialName("Helseinstitusjon")
    @JsonNames("HS")
    HS("Helseinstitusjon")
}
