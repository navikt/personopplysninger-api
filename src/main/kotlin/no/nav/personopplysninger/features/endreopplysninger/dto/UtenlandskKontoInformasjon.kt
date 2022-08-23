package no.nav.personopplysninger.features.endreopplysninger.dto

import kotlinx.serialization.Serializable

@Serializable
data class UtenlandskKontoInformasjon (
    val bank: Bank? = null,
    val landkode: String? = null,
    val landkodeTobokstavs: String? = null,
    val swift: String? = null,
    val valuta: String? = null,
)
