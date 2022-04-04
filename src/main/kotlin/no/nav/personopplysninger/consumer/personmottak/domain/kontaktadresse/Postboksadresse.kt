package no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse

class Postboksadresse(
        val postbokseier: String? = null,
        val postboks: String,
        val postnummer: String
) : Adresse()