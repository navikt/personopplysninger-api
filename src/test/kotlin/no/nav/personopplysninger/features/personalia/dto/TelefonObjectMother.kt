package no.nav.personopplysninger.features.personalia.dto


object TelefonObjectMother {
    val mobil = Telefon(mobil = "12345678", mobilKilde = "TEST")
    val mobilOgJobb = Telefon(mobil = "12345678", mobilKilde = "TEST", jobb = "87654321", jobbKilde = "TEST")
}
