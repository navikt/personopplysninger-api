package no.nav.personopplysninger.common.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kode(
        val navn: String,
        val betydninger: List<Betydning>
)
