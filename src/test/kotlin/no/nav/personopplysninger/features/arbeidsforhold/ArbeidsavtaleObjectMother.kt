package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsavtale

object ArbeidsavtaleObjectMother {

    val withDummyValues = Arbeidsavtale(
            antallTimerPrUke = 37.5,
            arbeidstidsordning = "Fast",
            stillingsprosent = 100.0,
            yrke = "YRK",
            sistStillingsendring = "IFJOR",
            sistLoennsendring = "IFJOR"

    )

    val arrayOfDummyValues = arrayOf(
            ArbeidsavtaleObjectMother.withDummyValues,
            Arbeidsavtale(

            )
    )
}
