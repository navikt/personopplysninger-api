package no.nav.personopplysninger.common.consumer.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kode(
        val navn: String,
        val betydninger: List<Betydning>
)
