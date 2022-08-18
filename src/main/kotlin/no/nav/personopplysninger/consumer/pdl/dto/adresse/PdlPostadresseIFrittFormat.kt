package no.nav.personopplysninger.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlPostadresseIFrittFormat(
    val adresselinje1: String? = null,
    val adresselinje2: String? = null,
    val adresselinje3: String? = null,
    val postnummer: String? = null
)