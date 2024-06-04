package no.nav.personopplysninger.consumer.kontoregister.dto.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Valutakode(
    @JsonNames("valutakode")
    val kode: String,
    @JsonNames("valuta")
    val tekst: String,
)