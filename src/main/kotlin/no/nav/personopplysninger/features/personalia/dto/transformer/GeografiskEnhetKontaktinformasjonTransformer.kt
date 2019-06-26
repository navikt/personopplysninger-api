package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.norg2.domain.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk


object GeografiskEnhetKontaktinformasjonTransformer {
    fun toOutbound(inbound: Norg2EnhetKontaktinfo) = GeografiskEnhetKontaktInformasjon(
            publikumsmottak = PublikumsmottakTransformer.toOutbound(inbound?.publikumsmottak),
            tlfperson = inbound.telefonnummer,
            spesielleopplysninger = inbound.spesielleOpplysninger,
            postadresse = NavkontorPostadresseTransformer.toOutbound(inbound?.postadresse)

    )

}