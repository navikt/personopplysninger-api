package no.nav.personopplysninger.features.personalia.dto.outbound


data class Tlfnr(
        /* Telefonnummer jobb */
        val jobb: String? = null,
        /* Telefonnummer mobil */
        val mobil: String? = null,
        /* Telefonnummer privat */
        val privat: String? = null,
        val datakilder: Set<Kilde>
)