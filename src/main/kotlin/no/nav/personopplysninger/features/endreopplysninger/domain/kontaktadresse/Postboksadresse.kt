package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.AdresseType.POSTBOKSADRESSE

class Postboksadresse(
        val postbokseier: String? = null,
        val postboks: String,
        val postnummer: String
) : Adresse(POSTBOKSADRESSE)