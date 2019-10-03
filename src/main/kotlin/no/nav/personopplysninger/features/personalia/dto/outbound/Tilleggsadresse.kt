package no.nav.personopplysninger.features.personalia.dto.outbound


data class Tilleggsadresse(

        val adresse1: String? = null,
        val adresse2: String? = null,
        val adresse3: String? = null,
        val bolignummer: String? = null,
        val bydel: String? = null,
        /* Dato gyldig fra, format (ISO-8601): yyyy-MM-dd */
        val datoFraOgMed: String? = null,
        /* Dato til når informasjonen er gyldig, format (ISO-8601): yyyy-MM-dd */
        val datoTilOgMed: String? = null,
        val gateKode: String? = null,
        val husbokstav: String? = null,
        val husnummer: String? = null,
        val kommunenummer: String? = null,
        val kommune: String? = null,
        val postboksanlegg: String? = null,
        val postboksnummer: String? = null,
        val postnummer: String? = null,
        val poststed: String? = null
) {
    fun getType(): String {
        if (!postboksnummer.isNullOrEmpty()) {
            return "POSTBOKSADRESSE"
        } else if (husnummer.isNullOrEmpty() || husnummer.equals("0000")) {
            return "STEDSADRESSE"
        } else {
            return "GATEADRESSE"
        }
    }
}