package no.nav.personopplysninger.consumer.pdl.dto.personalia

import kotlinx.serialization.Serializable

@Serializable
data class PdlKjoenn(
    val kjoenn: PdlKjoennType? = null,
)