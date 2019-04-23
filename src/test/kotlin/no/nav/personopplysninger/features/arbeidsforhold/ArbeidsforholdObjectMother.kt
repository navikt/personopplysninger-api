package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.domain.*

object ArbeidsforholdObjectMother {
    val withDummyValues = Arbeidsforhold(
            ansettelsesperiode = AnsettelsesPeriodeObjectMother.withDummyValues,
            antallTimerForTimeloennet = AntallTimerForTimeloennetObjectMother.arrayOfDummyValues,
            arbeidsavtaler = ArbeidsavtaleObjectMother.arrayOfDummyValues,
            arbeidsforholdId = "1111",
            arbeidsgiver = ArbeidsgiverObjectMother.withDummyValues,
            innrapportertEtterAOrdningen = true,
            permisjonPermitteringer = PermisjonPermitteringObjectMother.arrayOfDummyValues,
            sistBekreftet = "01.01.2016",
            type = "Organisasjon",
            utenlandsopphold = UtenlandsOppholdObjectMother.arrayOfDummyValues
    )

}