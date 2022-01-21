package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontaktadresse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.personopplysninger.features.endreopplysningergammel.domain.Endring
import no.nav.personopplysninger.features.endreopplysningergammel.domain.Error

@JsonIgnoreProperties(ignoreUnknown = true)
class EndringKontaktadresse : Endring<EndringKontaktadresse> {

    constructor() : super()
    private constructor(error: Error): super(error)

    companion object {
        fun validationError(error: Error): EndringKontaktadresse {
            return EndringKontaktadresse(error)
        }
    }
}

