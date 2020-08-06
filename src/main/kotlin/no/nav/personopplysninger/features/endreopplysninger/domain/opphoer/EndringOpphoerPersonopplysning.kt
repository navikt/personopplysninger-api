package no.nav.personopplysninger.features.endreopplysninger.domain.opphoer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring

@JsonIgnoreProperties(ignoreUnknown = true)
class EndringOpphoerPersonopplysning : Endring<EndringOpphoerPersonopplysning> {
    var innmeldtEndring: OpphoerPersonopplysning? = null

    constructor()

    constructor(innmeldtEndring: OpphoerPersonopplysning) {
        this.innmeldtEndring = innmeldtEndring
    }
}
