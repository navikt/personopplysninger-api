package no.nav.personopplysninger.consumer.pdlmottak.domain

data class PersonEndring (
        val personopplysninger: List<Personopplysning<*>>
)