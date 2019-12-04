package no.nav.personopplysninger.oppslag.kodeverk.exceptions

class KodeverkConsumerException : RuntimeException {
    constructor(msg: String, cause: Throwable) : super(msg, cause) {}
    constructor(msg: String) : super(msg) {}
}
