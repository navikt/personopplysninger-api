package no.nav.personopplysninger.consumer.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kode(
    val navn: String,
    val betydninger: List<Betydning>
)
