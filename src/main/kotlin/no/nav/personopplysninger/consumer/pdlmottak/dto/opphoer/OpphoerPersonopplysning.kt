package no.nav.personopplysninger.consumer.pdlmottak.dto.opphoer

import no.nav.personopplysninger.consumer.pdlmottak.dto.EndringsType.OPPHOER
import no.nav.personopplysninger.consumer.pdlmottak.dto.Personopplysning
import no.nav.personopplysninger.consumer.pdlmottak.dto.opphoer.OpphoerEndringsMelding.Companion.opphoerEndringsMelding

class OpphoerPersonopplysning(
        ident: String,
        opplysningsType: String,
        opplysningsId: String)
    : Personopplysning<OpphoerEndringsMelding>(ident, OPPHOER, opplysningsType, opphoerEndringsMelding, opplysningsId)