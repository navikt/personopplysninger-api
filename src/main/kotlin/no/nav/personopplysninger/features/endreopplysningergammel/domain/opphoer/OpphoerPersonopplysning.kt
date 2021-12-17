package no.nav.personopplysninger.features.endreopplysningergammel.domain.opphoer

import no.nav.personopplysninger.features.endreopplysningergammel.domain.EndringsType.OPPHOER
import no.nav.personopplysninger.features.endreopplysningergammel.domain.Personopplysning
import no.nav.personopplysninger.features.endreopplysningergammel.domain.opphoer.OpphoerEndringsMelding.Companion.opphoerEndringsMelding

class OpphoerPersonopplysning(
        ident: String,
        opplysningsType: String,
        opplysningsId: String)
    : Personopplysning<OpphoerEndringsMelding>(ident, OPPHOER, opplysningsType, opphoerEndringsMelding, opplysningsId)