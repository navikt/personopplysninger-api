package no.nav.personopplysninger.features.personaliagammel.dto.transformer

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk
import no.nav.personopplysninger.features.personaliagammel.dto.outbound.Kontaktinformasjon

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinfoBolk, fnr: String) = Kontaktinformasjon (
            epostadresse = inbound.kontaktinfo?.get(fnr)?.epostadresse,
            kanVarsles = inbound.kontaktinfo?.get(fnr)?.kanVarsles,
            mobiltelefonnummer = inbound.kontaktinfo?.get(fnr)?.mobiltelefonnummer,
            reservert = inbound.kontaktinfo?.get(fnr)?.reservert
    )
}
