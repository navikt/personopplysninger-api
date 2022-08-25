package no.nav.personopplysninger.endreopplysninger.dto.inbound

interface Endringsmelding {
    val subtype: String
    val kilde: String
}