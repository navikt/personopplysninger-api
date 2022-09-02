package no.nav.personopplysninger.personalia.consumer.tpsproxy.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kode (
    val kodeverk: String? = null,
    val verdi: String? = null
)