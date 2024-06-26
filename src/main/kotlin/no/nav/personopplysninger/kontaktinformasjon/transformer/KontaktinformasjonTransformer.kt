package no.nav.personopplysninger.kontaktinformasjon.transformer

import no.nav.personopplysninger.consumer.digdirkrr.dto.DigitalKontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.dto.Kontaktinformasjon

object KontaktinformasjonTransformer {
    fun toOutbound(inbound: DigitalKontaktinformasjon, spraakTerm: String?) = Kontaktinformasjon(
        epostadresse = inbound.epostadresse,
        mobiltelefonnummer = inbound.mobiltelefonnummer,
        reservert = inbound.reservert,
        spraak = spraakTerm?.let { if (it == "Norsk") "Bokmål" else it }
    )
}
