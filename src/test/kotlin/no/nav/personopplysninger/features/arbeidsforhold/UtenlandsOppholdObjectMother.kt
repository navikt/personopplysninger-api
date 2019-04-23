package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.domain.Utenlandsopphold

object UtenlandsOppholdObjectMother {
    val withDummyValues = Utenlandsopphold(
            periode = PeriodeObjectMother.withDummyValues,
            landkode = "NOR"
    )


    val arrayOfDummyValues = arrayOf(
            UtenlandsOppholdObjectMother.withDummyValues,
            Utenlandsopphold(

            )
    )
}
