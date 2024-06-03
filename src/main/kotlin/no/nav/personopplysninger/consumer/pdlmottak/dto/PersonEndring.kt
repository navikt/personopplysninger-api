package no.nav.personopplysninger.consumer.pdlmottak.dto

import kotlinx.serialization.Serializable

@Serializable
data class PersonEndring (
        val personopplysninger: List<Personopplysning>
)