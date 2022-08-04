package no.nav.personopplysninger.consumer.pdlmottak.dto

data class PersonEndring (
        val personopplysninger: List<Personopplysning<*>>
)