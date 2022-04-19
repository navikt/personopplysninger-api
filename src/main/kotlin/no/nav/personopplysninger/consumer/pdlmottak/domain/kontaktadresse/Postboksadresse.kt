package no.nav.personopplysninger.consumer.pdlmottak.domain.kontaktadresse

class Postboksadresse(
        val postbokseier: String? = null,
        val postboks: String,
        val postnummer: String
) : Adresse()