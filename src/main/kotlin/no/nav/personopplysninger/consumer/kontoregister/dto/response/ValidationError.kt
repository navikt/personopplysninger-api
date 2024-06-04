package no.nav.personopplysninger.consumer.kontoregister.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ValidationError(
    val feilmelding: String,
)