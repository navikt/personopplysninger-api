package no.nav.personopplysninger.consumer.pdlmottak.domain.kontaktadresse

import no.nav.personopplysninger.consumer.pdlmottak.domain.Endring
import no.nav.personopplysninger.consumer.pdlmottak.domain.Error

class EndringKontaktadresse : Endring<EndringKontaktadresse> {

    constructor() : super()
    private constructor(error: Error): super(error)

    companion object {
        fun validationError(error: Error): EndringKontaktadresse {
            return EndringKontaktadresse(error)
        }
    }
}

