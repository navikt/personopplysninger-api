package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsavtale

object ArbeidsavtaleObjectMother {

    val withDummyValues = Arbeidsavtale(
            antallTimerPrUke = 37.5,
            arbeidstidsordning = "Fast",
            stillingsprosent = 100.0,
            yrke = "YRK",
            sisteStillingsendring = "IFJOR",
            sisteLoennsendring = "IFJOR"

    )

    val arrayOfDummyValues = arrayOf(
            ArbeidsavtaleObjectMother.withDummyValues,
            Arbeidsavtale(

            )
    )
}
