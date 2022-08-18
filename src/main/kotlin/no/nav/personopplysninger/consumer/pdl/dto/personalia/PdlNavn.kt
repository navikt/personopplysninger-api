package no.nav.personopplysninger.consumer.pdl.dto.personalia

import kotlinx.serialization.Serializable

@Serializable
data class PdlNavn(
    val fornavn: String,
    val mellomnavn: String? = null,
    val etternavn: String
)