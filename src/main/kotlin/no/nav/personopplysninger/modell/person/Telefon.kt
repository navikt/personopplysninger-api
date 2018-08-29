package no.nav.personopplysninger.modell.person

data class Telefon(
        val jobb: String? = null,
        val jobbDatoRegistrert: String? = null,
        val jobbKilde: String? = null,
        val mobil: String? = null,
        val mobilDatoRegistrert: String? = null,
        val mobilKilde: String? = null,
        val privat: String? = null,
        val privatDatoRegistrert: String? = null,
        val privatKilde: String? = null
)