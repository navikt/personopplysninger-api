package no.nav.personopplysninger.kontaktinformasjon.transformer.testdata

import no.nav.personopplysninger.kontaktinformasjon.dto.inbound.DigitalKontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.dto.inbound.SikkerDigitalPostkasse

fun createDummyDigitalKontaktinfo(): DigitalKontaktinformasjon {
    return DigitalKontaktinformasjon(
        aktiv = true,
        spraak = "spraak",
        epostadresse = "hurra@test.no",
        kanVarsles = true,
        mobiltelefonnummer = "12345678",
        personident = "12345678",
        sikkerDigitalPostkasse = createDummySikkerDigitalPostkasse(),
        reservert = false
    )
}

private fun createDummySikkerDigitalPostkasse(): SikkerDigitalPostkasse {
    return SikkerDigitalPostkasse(
        adresse = "Testsvingen 51",
        leverandoerAdresse = "Testveien 15",
        leverandoerSertifikat = "leverandoersertifikat"
    )
}
