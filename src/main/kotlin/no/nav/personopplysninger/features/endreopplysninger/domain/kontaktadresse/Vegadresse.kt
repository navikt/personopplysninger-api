package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamVegadresse

class Vegadresse (
    val matrikkelId: String? = null,
    val bruksenhetsnummer: String? = null,
    val adressenavn: String? = null,
    val husnummer: String? = null,
    val husbokstav: String? = null,
    val tilleggsnavn: String? = null,
    val postnummer: String? = null
): Adresse(VEGADRESSE)