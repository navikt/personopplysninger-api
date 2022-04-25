package no.nav.personopplysninger.consumer.pdlmottak.domain.opphoer

import no.nav.personopplysninger.consumer.pdlmottak.domain.EndringsType.OPPHOER
import no.nav.personopplysninger.consumer.pdlmottak.domain.Personopplysning
import no.nav.personopplysninger.consumer.pdlmottak.domain.opphoer.OpphoerEndringsMelding.Companion.opphoerEndringsMelding

class OpphoerPersonopplysning(
        ident: String,
        opplysningsType: String,
        opplysningsId: String)
    : Personopplysning<OpphoerEndringsMelding>(ident, OPPHOER, opplysningsType, opphoerEndringsMelding, opplysningsId)