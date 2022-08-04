package no.nav.personopplysninger.consumer.pdlmottak.dto.kontaktadresse

import no.nav.personopplysninger.consumer.pdlmottak.dto.Endring
import no.nav.personopplysninger.consumer.pdlmottak.dto.Error

class EndringKontaktadresse : Endring<EndringKontaktadresse> {

    constructor() : super()
    private constructor(error: Error): super(error)

    companion object {
        fun validationError(error: Error): EndringKontaktadresse {
            return EndringKontaktadresse(error)
        }
    }
}

