package no.nav.personopplysninger.features.endreopplysninger.dto

data class UtenlandskKontoInformasjon (
    val bank: Bank? = null,
    val landkode: String? = null,
    val swift: String? = null,
    val valuta: String? = null,
)
