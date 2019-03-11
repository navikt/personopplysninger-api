package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinfoBolk) = no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon (
            epostadresse = inbound.kontaktinfo?.get("additionalProp1")?.epostadresse,
            kanVarsles = inbound.kontaktinfo?.get("additionalProp1")?.kanVarsles,
            mobiltelefonnummer = inbound.kontaktinfo?.get("additionalProp1")?.mobiltelefonnummer
    )
}
