package no.nav.personopplysninger.kontaktinformasjon.transformer

import no.nav.personopplysninger.consumer.digdirkrr.dto.DigitalKontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.dto.Kontaktinformasjon

fun DigitalKontaktinformasjon.toOutbound(spraakTerm: String?) = Kontaktinformasjon(
    epostadresse = epostadresse,
    mobiltelefonnummer = mobiltelefonnummer,
    reservert = reservert,
    spraak = if (spraakTerm == "Norsk") "Bokmål" else spraakTerm
)