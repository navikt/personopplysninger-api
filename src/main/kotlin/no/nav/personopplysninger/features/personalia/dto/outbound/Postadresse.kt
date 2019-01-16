package no.nav.personopplysninger.features.personalia.dto.outbound


data class Postadresse(

        val adresse1: String? = null,
        val adresse2: String? = null,
        val adresse3: String? = null,
        /* Dato gyldig fra, format (ISO-8601): yyyy-MM-dd */
        val datoFraOgMed: String? = null,
        val land: String? = null,
        val postnummer: String? = null
)