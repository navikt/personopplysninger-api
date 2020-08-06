package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

class Postboksadresse(
        val postbokseier: String? = null,
        val postboks: String,
        val postnummer: String
) : Adresse()