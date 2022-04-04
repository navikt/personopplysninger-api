package no.nav.personopplysninger.consumer.personmottak.domain

data class PersonEndring (
        val personopplysninger: List<Personopplysning<*>>
)