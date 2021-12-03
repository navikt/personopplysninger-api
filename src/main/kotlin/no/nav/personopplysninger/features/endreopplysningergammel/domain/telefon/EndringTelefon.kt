package no.nav.personopplysninger.features.endreopplysningergammel.domain.telefon

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.personopplysninger.features.endreopplysningergammel.domain.Endring

@JsonIgnoreProperties(ignoreUnknown = true)
class EndringTelefon : Endring<EndringTelefon> {
    var innmeldtEndring: Telefonnummer? = null

    constructor()

    constructor(innmeldtEndring: Telefonnummer) {
        this.innmeldtEndring = innmeldtEndring
    }
}
