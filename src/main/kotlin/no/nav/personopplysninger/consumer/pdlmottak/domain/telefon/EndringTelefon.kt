package no.nav.personopplysninger.consumer.pdlmottak.domain.telefon

import no.nav.personopplysninger.consumer.pdlmottak.domain.Endring

class EndringTelefon : Endring<EndringTelefon> {
    var innmeldtEndring: Telefonnummer? = null

    constructor()

    constructor(innmeldtEndring: Telefonnummer) {
        this.innmeldtEndring = innmeldtEndring
    }
}
