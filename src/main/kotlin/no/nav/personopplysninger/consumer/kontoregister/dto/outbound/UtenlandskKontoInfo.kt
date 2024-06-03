package no.nav.personopplysninger.consumer.kontoregister.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class UtenlandskKontoInfo(
    val banknavn: String = "",
    val bankkode: String? = null,
    val bankLandkode: String = "",
    val valutakode: String,
    val swiftBicKode: String? = null,
    val bankadresse1: String = "",
    val bankadresse2: String = "",
    val bankadresse3: String = "",
)
