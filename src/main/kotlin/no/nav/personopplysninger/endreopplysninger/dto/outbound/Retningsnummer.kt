package no.nav.personopplysninger.endreopplysninger.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Retningsnummer(
        val landskode: String,
        val land: String?
)
