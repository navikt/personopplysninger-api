package no.nav.personopplysninger.consumer.kontoregister.domain

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
