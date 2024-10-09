package no.nav.personopplysninger.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Publikumsmottak(
    val besoeksadresse: Adresse?,
    val aapningstider: List<Aapningstider>?,
    val stedsbeskrivelse: String?,
    val adkomstbeskrivelse: String?,
)