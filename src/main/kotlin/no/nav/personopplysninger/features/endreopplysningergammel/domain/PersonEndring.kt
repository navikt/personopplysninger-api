package no.nav.personopplysninger.features.endreopplysningergammel.domain

data class PersonEndring (
        val personopplysninger: List<Personopplysning<*>>
)