package no.nav.personopplysninger.features.endreopplysninger.dto

data class Kontonummer(
    val kilde: String = "BRUKER SELV",
    val utenlandskKontoInformasjon: UtenlandskKontoInformasjon? = null,
    val value: String,
)