package no.nav.personopplysninger.features.personaliagammel.dto.outbound


data class Veiadresse(

        /* Husbokstav */
        val bokstav: String? = null,
        val bolignummer: String? = null,
        val gatekode: String? = null,
        val husnummer: String? = null
)