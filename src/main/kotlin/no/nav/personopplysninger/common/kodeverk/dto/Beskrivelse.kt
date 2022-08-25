package no.nav.personopplysninger.common.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
data class Beskrivelse(
        val term: String,
        val tekst: String? = null
)
