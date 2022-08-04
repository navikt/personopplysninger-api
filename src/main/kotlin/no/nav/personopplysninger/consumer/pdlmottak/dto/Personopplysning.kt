package no.nav.personopplysninger.consumer.pdlmottak.dto

abstract class Personopplysning<T> (
        val ident: String,
        val endringstype: EndringsType,
        val opplysningstype: String,
        val endringsmelding: T,
        val opplysningsId: String?
) {
    fun asSingleEndring() : PersonEndring {
        return PersonEndring(personopplysninger = listOf(this))
    }
}