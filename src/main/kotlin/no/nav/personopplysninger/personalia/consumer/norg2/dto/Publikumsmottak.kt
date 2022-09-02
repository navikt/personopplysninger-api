package no.nav.personopplysninger.personalia.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Publikumsmottak(
    val id: String? = null,
    val besoeksadresse: Besoeksadresse? = null,
    val aapningstider: List<Aapningstider>? = null,
    val stedsbeskrivelse: String? = null
)
