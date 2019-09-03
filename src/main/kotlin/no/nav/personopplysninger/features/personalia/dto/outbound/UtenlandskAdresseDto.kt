package no.nav.personopplysninger.features.personalia.dto.outbound


data class UtenlandskAdresseDto(

        val adresse1: String? = null,
        val adresse2: String? = null,
        val adresse3: String? = null,
        /* Dato gyldig fra, format (ISO-8601): yyyy-MM-dd */
        val datoFraOgMed: String? = null,
        /* Dato til når informasjonen er gyldig, format (ISO-8601): yyyy-MM-dd */
        val datoTilOgMed: String? = null,
        /* Landkode */
        val land: String? = null
)