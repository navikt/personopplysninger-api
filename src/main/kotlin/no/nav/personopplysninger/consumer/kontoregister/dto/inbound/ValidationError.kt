package no.nav.personopplysninger.consumer.kontoregister.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class ValidationError(
    val feilmelding: String,
)