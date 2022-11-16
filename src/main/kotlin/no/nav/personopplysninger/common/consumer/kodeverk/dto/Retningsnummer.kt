package no.nav.personopplysninger.common.consumer.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
data class Retningsnummer(
    val landskode: String,
    val land: String?
)
