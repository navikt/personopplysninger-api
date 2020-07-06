package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.AdresseType.UTENLANDSK_ADRESSE


class UtenlandskAdresse(
        val adressenavnNummer: String? = null,
        val bygningEtasjeLeilighet: String? = null,
        val postboksNummerNavn: String? = null,
        val postkode: String? = null,
        val bySted: String? = null,
        val regionDistriktOmraade: String? = null,
        val landkode: String
): Adresse(UTENLANDSK_ADRESSE)