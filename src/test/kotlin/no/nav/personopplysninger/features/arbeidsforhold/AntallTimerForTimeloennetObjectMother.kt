package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.domain.*

object AntallTimerForTimeloennetObjectMother {
    val withDummyValues = AntallTimerForTimeloennet(
            antallTimer = 37.5,
            periode = PeriodeObjectMother.withDummyValues
    )

    val arrayOfDummyValues = arrayOf(
            AntallTimerForTimeloennetObjectMother.withDummyValues,
            AntallTimerForTimeloennet(

            )
    )

}