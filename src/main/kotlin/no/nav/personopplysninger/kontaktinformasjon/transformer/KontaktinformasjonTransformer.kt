package no.nav.personopplysninger.kontaktinformasjon.transformer

import no.nav.personopplysninger.kontaktinformasjon.dto.inbound.DigitalKontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.dto.outbound.Kontaktinformasjon

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinformasjon, spraakTerm: String) = Kontaktinformasjon(
        epostadresse = inbound.epostadresse,
        kanVarsles = inbound.kanVarsles,
        mobiltelefonnummer = inbound.mobiltelefonnummer,
        reservert = inbound.reservert,
        spraak = if (spraakTerm == "Norsk") "Bokmål" else spraakTerm
    )
}
