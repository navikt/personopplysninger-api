package no.nav.personopplysninger.features.personaliagammel.dto.outbound


data class GeografiskTilknytning(
        val bydel: String? = null,
        /* Dato gyldig, format (ISO-8601): yyyy-MM-dd */
        val datoFraOgMed: String? = null,
        val kommune: String? = null,
        val land: String? = null,
        var enhet: String? = null
)