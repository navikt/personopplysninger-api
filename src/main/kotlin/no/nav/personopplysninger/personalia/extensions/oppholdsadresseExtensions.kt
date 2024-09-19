package no.nav.personopplysninger.personalia.extensions

import no.nav.pdl.generated.dto.hentpersonquery.Oppholdsadresse
import no.nav.personopplysninger.personalia.enums.AdresseMappingType

val Oppholdsadresse.mappingType: AdresseMappingType
    get() = if (vegadresse != null) {
        AdresseMappingType.INNLAND_VEGADRESSE
    } else if (matrikkeladresse != null) {
        AdresseMappingType.MATRIKKELADRESSE
    } else if (utenlandskAdresse != null) {
        AdresseMappingType.UTLAND_ADRESSE
    } else {
        AdresseMappingType.EMPTY
    }

val Oppholdsadresse.postnummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.postnummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.postnummer
        else -> null
    }

val Oppholdsadresse.landkode: String?
    get() = when (mappingType) {
        AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse?.landkode
        else -> null
    }

val Oppholdsadresse.kommunenummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.kommunenummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.kommunenummer
        else -> null
    }
