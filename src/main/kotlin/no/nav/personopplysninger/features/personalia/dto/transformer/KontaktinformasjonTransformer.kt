package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.personalia.kontaktinformasjon.DigitalKontaktinformasjon

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinformasjon, spraakTerm: String) = Kontaktinformasjon(
        epostadresse = inbound.epostadresse,
        kanVarsles = inbound.kanVarsles,
        mobiltelefonnummer = inbound.mobiltelefonnummer,
        reservert = inbound.reservert,
        spraak = if (spraakTerm.equals("Norsk")) "Bokmål" else spraakTerm
    )
}
