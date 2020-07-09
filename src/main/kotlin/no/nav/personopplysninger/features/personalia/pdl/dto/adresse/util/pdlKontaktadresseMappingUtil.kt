package no.nav.personopplysninger.features.personalia.pdl.dto.adresse.util

import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadressetype.Innland
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadressetype.Utland
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.util.PdlKontaktadresseMappingType.*

val PdlKontaktadresse.mappingType: PdlKontaktadresseMappingType get() {
    return if (type == Innland && vegadresse != null) {
        INNLAND_VEGADRESSE
    } else if (type == Innland && postadresseIFrittFormat != null) {
        INNLAND_FRIFORMADRESSE
    } else if (type == Innland && postboksadresse != null) {
        INNLAND_POSTBOKSADRESSE
    } else if (type == Utland && utenlandskAdresse != null) {
        UTLAND_ADRESSE
    } else if (type == Utland && utenlandskAdresseIFrittFormat != null) {
        UTLAND_FRIFORMADRESSE
    } else {
        EMPTY
    }
}

val PdlKontaktadresse.postnummer: String? get() {
    return when (mappingType) {
        INNLAND_VEGADRESSE -> vegadresse!!.postnummer
        INNLAND_FRIFORMADRESSE -> postadresseIFrittFormat!!.postnummer
        INNLAND_POSTBOKSADRESSE -> postboksadresse!!.postnummer
        UTLAND_ADRESSE, UTLAND_FRIFORMADRESSE, EMPTY -> null
    }
}

val PdlKontaktadresse.landkode: String? get() {
    return when (mappingType) {
        UTLAND_ADRESSE -> utenlandskAdresse!!.landkode
        UTLAND_FRIFORMADRESSE -> utenlandskAdresseIFrittFormat!!.landkode
        INNLAND_VEGADRESSE, INNLAND_FRIFORMADRESSE, INNLAND_POSTBOKSADRESSE, EMPTY -> null
    }
}