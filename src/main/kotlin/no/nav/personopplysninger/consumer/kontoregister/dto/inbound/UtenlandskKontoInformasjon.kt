package no.nav.personopplysninger.consumer.kontoregister.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class UtenlandskKontoInformasjon (
    val bank: Bank? = null,
    val landkode: String? = null,
    val swift: String? = null,
    val valuta: String,
)
