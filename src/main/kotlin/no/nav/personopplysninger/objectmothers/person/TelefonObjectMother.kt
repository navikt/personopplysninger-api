package no.nav.personopplysninger.objectmothers.person

import no.nav.personopplysninger.modell.person.Telefon


object TelefonObjectMother{
    fun getMobil(): Telefon {
        return Telefon(mobil = "12345678", mobilKilde = "TEST")
    }

    fun getMobilOgJobb(): Telefon {
        return Telefon(mobil = "12345678", mobilKilde = "TEST",
                jobb = "87654321", jobbKilde = "TEST")
    }

}
