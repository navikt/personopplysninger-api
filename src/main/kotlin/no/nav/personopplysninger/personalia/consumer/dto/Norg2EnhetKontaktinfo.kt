package no.nav.personopplysninger.personalia.consumer.dto

import kotlinx.serialization.Serializable

@Serializable
data class Norg2EnhetKontaktinfo(
    val id: String? = null,
    val telefonnummer: String? = null,
    val faksnummer: String? = null,
    val postadresse: Postadresse? = null,
    val besoeksadresse: Besoeksadresse? = null,
    val spesielleOpplysninger: String? = null,
    val publikumsmottak: List<Publikumsmottak>? = null,
    val stedsbeskrivelse: String? = null
)
