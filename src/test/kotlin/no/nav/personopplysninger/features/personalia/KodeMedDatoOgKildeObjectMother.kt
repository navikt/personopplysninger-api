package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Kode
import no.nav.tps.person.KodeMedDatoOgKilde

object KodeMedDatoOgKildeObjectMother {
    val dummyValues = KodeMedDatoOgKilde(
            datoFraOgMed = "dummy dato",
            kilde = "dummy kilde",
            kode = Kode(
                    kodeverk = "dummy kodeverk",
                    verdi = "dummy verdi"
            )
    )

    val arrayOfDummyValues = arrayOf(
            dummyValues,
            KodeMedDatoOgKilde(
                    datoFraOgMed = "dummy dato 2",
                    kilde = "dummy kilde 2",
                    kode = Kode(
                            kodeverk = "dummy kodeverk 2",
                            verdi = "dummy verdi 2"
                    )
            )
    )

    fun medKode(kode: String) = dummyValues.copy(kode = dummyValues.kode!!.copy(verdi = kode))
}