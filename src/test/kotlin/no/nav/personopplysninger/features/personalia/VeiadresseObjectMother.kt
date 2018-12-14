package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Veiadresse

object VeiadresseObjectMother {
    fun vardeveien7(): Veiadresse {
        return Veiadresse(bokstav = "A", bolignummer = "H102", gatekode = "12", husnummer = "7")
    }

    fun veiadresseNullObject(): Veiadresse {
        return Veiadresse(bokstav = null, bolignummer = null, gatekode = null, husnummer = null)
    }
}