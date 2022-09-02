package no.nav.personopplysninger.common.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlUkjentbosted(
    val bostedskommune: String? = null
)
