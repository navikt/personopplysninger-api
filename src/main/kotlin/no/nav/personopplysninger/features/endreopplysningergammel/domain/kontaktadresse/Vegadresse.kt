package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontaktadresse

class Vegadresse (
        val matrikkelId: String? = null,
        val bruksenhetsnummer: String? = null,
        val adressenavn: String? = null,
        val husnummer: String? = null,
        val husbokstav: String? = null,
        val tilleggsnavn: String? = null,
        val postnummer: String? = null
): Adresse()