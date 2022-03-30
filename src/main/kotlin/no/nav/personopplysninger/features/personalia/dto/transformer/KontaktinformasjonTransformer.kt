package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kontaktinformasjon.domain.DigitalKontaktinformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinformasjon, spraakTerm: String) = Kontaktinformasjon(
        epostadresse = inbound.epostadresse,
        kanVarsles = inbound.kanVarsles,
        mobiltelefonnummer = inbound.mobiltelefonnummer,
        reservert = inbound.reservert,
        spraak = if (spraakTerm == "Norsk") "Bokmål" else spraakTerm
    )
}
