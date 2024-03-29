package no.nav.personopplysninger.common.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlPostboksadresse(
    val postbokseier: String? = null,
    val postboks: String,
    val postnummer: String? = null
)