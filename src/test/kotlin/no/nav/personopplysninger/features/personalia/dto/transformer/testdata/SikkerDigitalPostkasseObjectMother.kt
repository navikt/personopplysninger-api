package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.dkif.kontaktinformasjon.SikkerDigitalPostkasse

object SikkerDigitalPostkasseObjectMother {
    val withAllValues = SikkerDigitalPostkasse(
            adresse = "Testsvingen 51",
            leverandoerAdresse = "Testveien 15",
            leverandoerSertifikat = "leverandoersertifikat"
    )
}
