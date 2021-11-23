package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk

object DigitalKontaktinfoBolkObjectMother {
    val medKontaktinfo = DigitalKontaktinfoBolk(
            feil = null,
            kontaktinfo = mapOf("12345678" to DigitalKontaktinfoObjectMother.withAllValues)
    )

    val medFeil = DigitalKontaktinfoBolk(
            feil = mapOf("12345678" to FeilObjectMother.withAllValues),
            kontaktinfo = null
    )
}
