package no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Landkode(
    @JsonNames("landkode")
    val kode: String,
    @JsonNames("land")
    val tekst: String,
    val kreverIban: Boolean,
    val ibanLengde: Int? = null,
    val kreverBankkode: Boolean,
    val bankkodeLengde: Int? = null,
)