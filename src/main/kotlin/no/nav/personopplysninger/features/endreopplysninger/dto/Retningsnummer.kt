package no.nav.personopplysninger.features.endreopplysninger.dto

import kotlinx.serialization.Serializable

@Serializable
data class Retningsnummer(
        val landskode: String,
        val land: String?
)
