package no.nav.personopplysninger.features.personalia.tps

import no.nav.tps.person.Telefoninfo

object TelefoninfoObjectMother {
    val dummyValues = Telefoninfo(
            jobb = "dummy jobb tlfnr",
            jobbDatoRegistrert = dummyDato,
            jobbKilde = dummyKilde,
            mobil = "dummy mobil tlfnr",
            mobilDatoRegistrert = dummyDato,
            mobilKilde = dummyKilde,
            privat = "dummy privat tlfnr",
            privatDatoRegistrert = dummyDato,
            privatKilde = dummyKilde
    )

}
