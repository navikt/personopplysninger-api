package no.nav.personopplysninger.personalia.consumer.dto

import kotlinx.serialization.Serializable

@Serializable
data class Publikumsmottak(
    val id: String? = null,
    val besoeksadresse: Besoeksadresse? = null,
    val aapningstider: List<Aapningstider>? = null,
    val stedsbeskrivelse: String? = null
)
