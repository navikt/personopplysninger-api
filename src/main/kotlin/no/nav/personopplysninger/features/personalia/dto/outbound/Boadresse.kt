package no.nav.personopplysninger.features.personalia.dto.outbound


data class Boadresse(

        /* Primæradresse */
        val adresse: String? = null,
        val adressetillegg: String? = null,
        val bydel: String? = null,
        /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
        val datoFraOgMed: String? = null,
        val kommune: String? = null,
        val landkode: String? = null,
        val matrikkeladresse: Matrikkeladresse? = null,
        val postnummer: String? = null,
        val veiadresse: Veiadresse? = null
)