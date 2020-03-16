package no.nav.personopplysninger.features.personalia.dto.outbound


data class Tlfnr(
        /* Telefonnummer jobb */
        val jobb: String? = null,
        val landkodeJobb: String? = null,
        /* Telefonnummer mobil */
        val mobil: String? = null,
        val landkodeMobil: String? = null,
        /* Telefonnummer privat */
        val privat: String? = null,
        val landkodePrivat: String? = null
)