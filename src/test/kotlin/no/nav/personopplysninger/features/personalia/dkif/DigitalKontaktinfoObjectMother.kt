package no.nav.personopplysninger.features.personalia.dkif

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfo

object DigitalKontaktinfoObjectMother {
    val withAllValues = DigitalKontaktinfo(
        epostadresse = "hurra@test.no",
        kanVarsles = true,
        mobiltelefonnummer = "12345678",
        personident = "12345678",
        sikkerDigitalPostkasse = SikkerDigitalPostkasseObjectMother.withAllValues
    )
}
