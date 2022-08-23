package no.nav.personopplysninger.features.endreopplysninger.dto

import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    val adresseLinje1: String? = null,
    val adresseLinje2: String? = null,
    val adresseLinje3: String? = null,
    val kode: String? = null,
    val navn: String? = null,
)
