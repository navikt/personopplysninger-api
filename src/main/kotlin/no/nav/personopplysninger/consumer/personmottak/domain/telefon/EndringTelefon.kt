package no.nav.personopplysninger.consumer.personmottak.domain.telefon

import no.nav.personopplysninger.consumer.personmottak.domain.Endring

class EndringTelefon : Endring<EndringTelefon> {
    var innmeldtEndring: Telefonnummer? = null

    constructor()

    constructor(innmeldtEndring: Telefonnummer) {
        this.innmeldtEndring = innmeldtEndring
    }
}
