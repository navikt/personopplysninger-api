package no.nav.personopplysninger.common.kontoregister.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class UtenlandskKontoInfo(
    val banknavn: String = "",
    val bankkode: String = "",
    val bankLandkode: String = "",
    val valutakode: String,
    val swiftBicKode: String = "",
    val bankadresse1: String = "",
    val bankadresse2: String = "",
    val bankadresse3: String = "",
)
