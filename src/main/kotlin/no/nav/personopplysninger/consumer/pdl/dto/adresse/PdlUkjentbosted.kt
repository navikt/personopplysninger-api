package no.nav.personopplysninger.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlUkjentbosted(
    val bostedskommune: String? = null
)
