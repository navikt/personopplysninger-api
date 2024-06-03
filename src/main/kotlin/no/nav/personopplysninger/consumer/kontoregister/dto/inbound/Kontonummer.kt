package no.nav.personopplysninger.consumer.kontoregister.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class Kontonummer(
    val kilde: String = "BRUKER SELV",
    val utenlandskKontoInformasjon: UtenlandskKontoInformasjon? = null,
    val value: String,
)