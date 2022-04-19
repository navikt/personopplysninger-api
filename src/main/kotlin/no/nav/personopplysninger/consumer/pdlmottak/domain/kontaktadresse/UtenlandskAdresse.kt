package no.nav.personopplysninger.consumer.pdlmottak.domain.kontaktadresse

class UtenlandskAdresse(
        val adressenavnNummer: String? = null,
        val bygningEtasjeLeilighet: String? = null,
        val postboksNummerNavn: String? = null,
        val postkode: String? = null,
        val bySted: String? = null,
        val regionDistriktOmraade: String? = null,
        val landkode: String
): Adresse()