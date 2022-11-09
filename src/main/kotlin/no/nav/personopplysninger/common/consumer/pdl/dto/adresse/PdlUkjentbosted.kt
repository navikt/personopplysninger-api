package no.nav.personopplysninger.common.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlUkjentbosted(
    val bostedskommune: String? = null
)
