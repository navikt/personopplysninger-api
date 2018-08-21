package no.nav.personopplysninger_backend.modell.person

data class Telefon(
        val jobb: String,
        val jobbDatoRegistrert: String,
        val jobbKilde: String,
        val mobil: String,
        val mobilDatoRegistrert: String,
        val mobilKilde: String,
        val privat: String,
        val privatDatoRegistrert: String,
        val privatKilde: String
)