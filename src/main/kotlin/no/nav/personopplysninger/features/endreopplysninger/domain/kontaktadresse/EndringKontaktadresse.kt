package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import no.nav.personopplysninger.features.endreopplysninger.domain.Endring
import no.nav.personopplysninger.features.endreopplysninger.domain.Error

class EndringKontaktadresse : Endring<EndringKontaktadresse> {

    constructor() : super()
    private constructor(error: Error): super(error)

    companion object {
        fun validationError(error: Error): EndringKontaktadresse {
            return EndringKontaktadresse(error)
        }
    }
}

