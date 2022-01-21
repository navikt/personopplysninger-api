package no.nav.personopplysninger.features.personaliagammel.dto.outbound


data class Tlfnr (
        /* Telefonnummer jobb */
        val telefonAlternativ: String? = null,
        val landskodeAlternativ: String? = null,
        /* Telefonnummer mobil */
        val telefonHoved: String? = null,
        val landskodeHoved: String? = null
)