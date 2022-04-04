package no.nav.personopplysninger.consumer.personmottak.domain.opphoer

import no.nav.personopplysninger.consumer.personmottak.domain.EndringsType.OPPHOER
import no.nav.personopplysninger.consumer.personmottak.domain.Personopplysning
import no.nav.personopplysninger.consumer.personmottak.domain.opphoer.OpphoerEndringsMelding.Companion.opphoerEndringsMelding

class OpphoerPersonopplysning(
        ident: String,
        opplysningsType: String,
        opplysningsId: String)
    : Personopplysning<OpphoerEndringsMelding>(ident, OPPHOER, opplysningsType, opphoerEndringsMelding, opplysningsId)