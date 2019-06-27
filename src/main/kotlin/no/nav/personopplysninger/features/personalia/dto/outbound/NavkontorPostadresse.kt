package no.nav.personopplysninger.features.personalia.dto.outbound


data class NavkontorPostadresse(

        val type : kotlin.String? = null,
        val postnummer : kotlin.String? = null,
        val poststed : kotlin.String? = null,
        val gatenavn: kotlin.String? = null,
        val husnummer: kotlin.String? = null,
        val husbokstav: kotlin.String? = null
)