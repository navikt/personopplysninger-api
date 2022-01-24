package no.nav.personopplysninger.exception

class ConsumerException : RuntimeException {

    constructor(msg: String, cause: Throwable) : super(msg, cause)

    constructor(msg: String) : super(msg)
}
