package no.nav.personopplysninger.features.arbeidsforhold

import jdk.nashorn.internal.runtime.JSType.toDouble
import no.nav.personopplysninger.features.arbeidsforhold.domain.PermisjonPermittering
import no.nav.personopplysninger.features.arbeidsforhold.domain.Person

object PermisjonPermitteringObjectMother {
    val withDummyValues = PermisjonPermittering(
            periode = PeriodeObjectMother.withDummyValues,
            prosent = 50.0,
            type = "Permisjon"

    )

    val arrayOfDummyValues = arrayOf(
            PermisjonPermitteringObjectMother.withDummyValues,
            PermisjonPermittering(

            )
    )
}
