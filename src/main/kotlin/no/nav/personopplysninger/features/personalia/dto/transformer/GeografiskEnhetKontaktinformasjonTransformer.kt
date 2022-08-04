package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.norg2.dto.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon

object GeografiskEnhetKontaktinformasjonTransformer {
    fun toOutbound(inbound: Norg2EnhetKontaktinfo) = GeografiskEnhetKontaktInformasjon(
            publikumsmottak = PublikumsmottakTransformer.toOutbound(inbound.publikumsmottak),
            tlfperson = inbound.telefonnummer,
            spesielleopplysninger = inbound.spesielleOpplysninger,
            postadresse = NavkontorPostadresseTransformer.toOutbound(inbound.postadresse)
    )
}
