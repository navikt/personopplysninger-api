package no.nav.personopplysninger.features.endreopplysninger.domain.telefon

import no.nav.personopplysninger.features.endreopplysninger.domain.Endring

class EndringTelefon : Endring<EndringTelefon> {
    var innmeldtEndring: Telefonnummer? = null

    constructor()

    constructor(innmeldtEndring: Telefonnummer) {
        this.innmeldtEndring = innmeldtEndring
    }
}
