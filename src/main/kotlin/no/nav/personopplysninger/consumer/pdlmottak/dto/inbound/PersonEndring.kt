package no.nav.personopplysninger.consumer.pdlmottak.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class PersonEndring(
    val personopplysninger: List<Personopplysning>
)