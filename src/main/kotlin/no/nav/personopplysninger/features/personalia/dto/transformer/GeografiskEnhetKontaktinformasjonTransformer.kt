package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.norg2.domain.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk


object GeografiskEnhetKontaktinformasjonTransformer {
    fun toOutbound(inbound: Norg2EnhetKontaktinfo) = GeografiskEnhetKontaktInformasjon(

            gateadresse = inbound.publikumsmottak?.get(0)?.besoeksadresse?.gatenavn,
            poststed = inbound.publikumsmottak?.get(0)?.besoeksadresse?.poststed,
            aapningmandag = AapningstidTransformer.toOutbound(inbound.aapningstider?.get(0)),
            aapningtirsdag = AapningstidTransformer.toOutbound(inbound.aapningstider?.get(1)),
            appningonsdag = AapningstidTransformer.toOutbound(inbound.aapningstider?.get(2)),
            aapningtorsdag = AapningstidTransformer.toOutbound(inbound.aapningstider?.get(3)),
            aapningfredag = AapningstidTransformer.toOutbound(inbound.aapningstider?.get(5)),
            tlfperson = inbound.publikumsmottak?.get(0)?.besoeksadresse?.gatenavn,
            tlfpensjon = inbound.telefonnummer
    )

}