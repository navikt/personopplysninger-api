package no.nav.personopplysninger.common.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlMatrikkeladresse(
    val bruksenhetsnummer: String? = null,
    val tilleggsnavn: String? = null,
    val postnummer: String? = null,
    val kommunenummer: String? = null
)
