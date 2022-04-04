package no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse

import no.nav.personopplysninger.consumer.personmottak.domain.Endring
import no.nav.personopplysninger.consumer.personmottak.domain.Error

class EndringKontaktadresse : Endring<EndringKontaktadresse> {

    constructor() : super()
    private constructor(error: Error): super(error)

    companion object {
        fun validationError(error: Error): EndringKontaktadresse {
            return EndringKontaktadresse(error)
        }
    }
}

