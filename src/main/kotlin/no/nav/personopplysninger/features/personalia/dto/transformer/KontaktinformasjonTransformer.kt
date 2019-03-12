package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinfoBolk, fnr: String) = no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon (
            epostadresse = inbound.kontaktinfo?.get(fnr)?.epostadresse,
            kanVarsles = inbound.kontaktinfo?.get(fnr)?.kanVarsles,
            mobiltelefonnummer = inbound.kontaktinfo?.get(fnr)?.mobiltelefonnummer
    )
}
