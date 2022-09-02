package no.nav.personopplysninger.endreopplysninger.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class PersonEndring (
        val personopplysninger: List<Personopplysning>
)