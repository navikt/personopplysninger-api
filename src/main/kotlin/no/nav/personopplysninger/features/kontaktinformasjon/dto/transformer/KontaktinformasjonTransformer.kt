package no.nav.personopplysninger.features.kontaktinformasjon.dto.transformer

import no.nav.personopplysninger.consumer.kontaktinformasjon.dto.DigitalKontaktinformasjon
import no.nav.personopplysninger.features.kontaktinformasjon.dto.outbound.Kontaktinformasjon

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinformasjon, spraakTerm: String) = Kontaktinformasjon(
        epostadresse = inbound.epostadresse,
        kanVarsles = inbound.kanVarsles,
        mobiltelefonnummer = inbound.mobiltelefonnummer,
        reservert = inbound.reservert,
        spraak = if (spraakTerm == "Norsk") "Bokmål" else spraakTerm
    )
}
