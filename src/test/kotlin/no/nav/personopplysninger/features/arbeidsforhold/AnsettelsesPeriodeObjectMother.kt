package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.domain.Ansettelsesperiode

object AnsettelsesPeriodeObjectMother {

    val withDummyValues = Ansettelsesperiode(
        periode = PeriodeObjectMother.withDummyValues
    )


}
