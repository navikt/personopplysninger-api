package no.nav.personopplysninger.features.endreopplysninger.domain.opphoer

import no.nav.personopplysninger.features.endreopplysninger.domain.EndringsType.OPPHOER
import no.nav.personopplysninger.features.endreopplysninger.domain.Personopplysning
import no.nav.personopplysninger.features.endreopplysninger.domain.opphoer.OpphoerEndringsMelding.Companion.opphoerEndringsMelding

class OpphoerPersonopplysning(
        ident: String,
        opplysningsType: String,
        opplysningsId: String)
    : Personopplysning<OpphoerEndringsMelding>(ident, OPPHOER, opplysningsType, opphoerEndringsMelding, opplysningsId)