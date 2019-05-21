package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.norg2.domain.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon


object EnhetKontaktinfoTransformer {
    fun toOutbound(inbound: Norg2EnhetKontaktinfo) = GeografiskEnhetKontaktInformasjon (
        gateadresse = inbound.publikumsmottak?.besoeksadresse?.gatenavn,
        poststed = inbound.publikumsmottak?.besoeksadresse?.poststed,
        aapningmandag = inbound.aapningstider?.dag,
        aapningtirsdag = inbound.aapningstider?.dag,
        appningonsdag = inbound.aapningstider?.dag,
        aapningtorsdag = inbound.aapningstider?.dag,
        aapningfredag = inbound.aapningstider?.dag,
        andre = inbound.aapningstider?.dag,
        tlfperson = inbound.publikumsmottak?.besoeksadresse?.gatenavn,
        tlfpensjon = inbound.telefonnummer

    )

}