package no.nav.personopplysninger.consumer.inst.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
enum class Institusjonstype(val tekst: String) {

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
