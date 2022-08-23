package no.nav.personopplysninger.consumer.pdlmottak.dto.inbound

interface Endringsmelding {
    val subtype: String
    val kilde: String
}