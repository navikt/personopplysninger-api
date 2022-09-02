package no.nav.personopplysninger.common.pdl.dto.personalia

import kotlinx.serialization.Serializable

@Serializable
data class PdlKjoenn(
    val kjoenn: PdlKjoennType? = null,
)