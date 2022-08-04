package no.nav.personopplysninger.consumer.pdlmottak.dto.telefon

import no.nav.personopplysninger.consumer.pdlmottak.dto.Endring

class EndringTelefon : Endring<EndringTelefon> {
    var innmeldtEndring: Telefonnummer? = null

    constructor()

    constructor(innmeldtEndring: Telefonnummer) {
        this.innmeldtEndring = innmeldtEndring
    }
}
