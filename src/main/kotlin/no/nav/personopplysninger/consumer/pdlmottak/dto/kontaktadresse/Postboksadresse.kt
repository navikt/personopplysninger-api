package no.nav.personopplysninger.consumer.pdlmottak.dto.kontaktadresse

class Postboksadresse(
        val postbokseier: String? = null,
        val postboks: String,
        val postnummer: String
) : Adresse()