package no.nav.personopplysninger.consumer.kontoregister.domain

data class UtenlandskKontoInfo(
    val banknavn: String? = null,
    val bankkode: String? = null,
    val bankLandkode: String? = null,
    val valutakode: String,
    val swiftBicKode: String? = null,
    val bankadresse1: String? = null,
    val bankadresse2: String? = null,
    val bankadresse3: String? = null,
)
