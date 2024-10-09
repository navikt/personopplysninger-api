package no.nav.personopplysninger.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Brukerkontakt(
    val publikumsmottak: List<Publikumsmottak>
)