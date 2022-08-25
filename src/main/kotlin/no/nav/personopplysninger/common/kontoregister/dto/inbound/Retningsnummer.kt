package no.nav.personopplysninger.common.kontoregister.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class Retningsnummer(
        val landskode: String,
        val land: String?
)
