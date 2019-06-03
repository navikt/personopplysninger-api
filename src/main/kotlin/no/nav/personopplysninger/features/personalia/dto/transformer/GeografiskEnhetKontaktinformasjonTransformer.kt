package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.norg2.domain.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk


object GeografiskEnhetKontaktinformasjonTransformer {
    fun toOutbound(inbound: Norg2EnhetKontaktinfo) = GeografiskEnhetKontaktInformasjon(

            gateadresse = inbound.publikumsmottak?.get(0)?.besoeksadresse?.gatenavn,
            poststed = inbound.publikumsmottak?.get(0)?.besoeksadresse?.poststed,
            aapningmandag = inbound.aapningstider?.let {AapningstidTransformer.toOutbound(inbound.aapningstider?.get(0))},
            aapningtirsdag = inbound.aapningstider?.let {AapningstidTransformer.toOutbound(inbound.aapningstider?.get(1))},
            aapningonsdag = inbound.aapningstider?.let {AapningstidTransformer.toOutbound(inbound.aapningstider?.get(2))},
            aapningtorsdag = inbound.aapningstider?.let {AapningstidTransformer.toOutbound(inbound.aapningstider?.get(3))},
            aapningfredag = inbound.aapningstider?.let {AapningstidTransformer.toOutbound(inbound.aapningstider?.get(4))},
            tlfperson = inbound.telefonnummer,
            spesielleopplysninger = inbound.spesielleOpplysninger

    )

}