package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfo
import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk
import no.nav.dkif.kontaktinformasjon.Feil
import no.nav.dkif.kontaktinformasjon.SikkerDigitalPostkasse

fun createDummyDigitalKontaktinfoBolkMedKontaktinfo(): DigitalKontaktinfoBolk {
    return DigitalKontaktinfoBolk(
        feil = null,
        kontaktinfo = mapOf("12345678" to createDummyDigitalKontaktinfo())
    )
}

fun createDummyDigitalKontaktinfoBolkMedFeil(): DigitalKontaktinfoBolk {
    return DigitalKontaktinfoBolk(
        feil = mapOf("12345678" to createDummyFeil()),
        kontaktinfo = null
    )
}

private fun createDummyDigitalKontaktinfo(): DigitalKontaktinfo {
    return DigitalKontaktinfo(
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

private fun createDummyFeil(): Feil {
    return Feil(melding = "Ingen kontaktinformasjon registrert")
}
