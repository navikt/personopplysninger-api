package no.nav.personopplysninger.features.personaliagammel.dto.outbound


data class NavkontorPostadresse(

        val type : String? = null,
        val postnummer : String? = null,
        val poststed : String? = null,
        val postboksnummer : String? = null,
        val postboksanlegg : String? = null,
        val gatenavn: String? = null,
        val husnummer: String? = null,
        val husbokstav: String? = null,
        val adresseTilleggsnavn: String? = null
)
