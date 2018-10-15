package no.nav.personopplysninger.features.personalia.objectmothers.dto

import no.nav.personopplysninger.features.personalia.model.dto.Telefon


object TelefonObjectMother{
    fun getMobil(): Telefon {
        return Telefon(mobil = "12345678", mobilKilde = "TEST")
    }

    fun getMobilOgJobb(): Telefon {
        return Telefon(mobil = "12345678", mobilKilde = "TEST",
                jobb = "87654321", jobbKilde = "TEST")
    }

}
