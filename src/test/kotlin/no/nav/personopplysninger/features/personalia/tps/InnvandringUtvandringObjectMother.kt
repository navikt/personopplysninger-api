package no.nav.personopplysninger.features.personalia.tps

import no.nav.tps.person.InnvandringUtvandring

object InnvandringUtvandringObjectMother {
    val dummyValues = InnvandringUtvandring(
            innvandretDato = dummyDato,
            innvandretKilde = dummyKilde,
            innvandretLand = dummyKode,
            utvandretDato = dummyDato,
            utvandretKilde = dummyKilde,
            utvandretLand = dummyKode
    )


}