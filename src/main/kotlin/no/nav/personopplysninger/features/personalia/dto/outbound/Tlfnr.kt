package no.nav.personopplysninger.features.personalia.dto.outbound


data class Tlfnr (
        /* Telefonnummer jobb */
        val telefonAlternativ: String? = null,
        val landskodeAlternativ: String? = null,
        /* Telefonnummer mobil */
        val telefonHoved: String? = null,
        val landskodeHoved: String? = null
)