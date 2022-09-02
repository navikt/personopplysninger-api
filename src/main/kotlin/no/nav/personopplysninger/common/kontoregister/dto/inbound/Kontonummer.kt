package no.nav.personopplysninger.common.kontoregister.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class Kontonummer(
    val kilde: String = "BRUKER SELV",
    val utenlandskKontoInformasjon: UtenlandskKontoInformasjon? = null,
    val value: String,
)