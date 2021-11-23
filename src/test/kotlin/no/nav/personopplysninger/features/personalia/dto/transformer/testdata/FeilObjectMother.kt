package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.dkif.kontaktinformasjon.Feil

object FeilObjectMother {
    val withAllValues = Feil(
            melding = "Ingen kontaktinformasjon registrert"
    )
}
