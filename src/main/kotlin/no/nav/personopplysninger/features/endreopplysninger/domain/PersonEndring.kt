package no.nav.personopplysninger.features.endreopplysninger.domain

data class PersonEndring (
        val personopplysninger: List<Personopplysning<*>>
)