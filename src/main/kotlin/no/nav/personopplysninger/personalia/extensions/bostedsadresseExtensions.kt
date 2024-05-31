package no.nav.personopplysninger.personalia.extensions

import no.nav.pdl.generated.dto.hentpersonquery.Bostedsadresse
import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.AdresseMappingType

val Bostedsadresse.mappingType: AdresseMappingType
    get() = if (vegadresse != null) {
        AdresseMappingType.INNLAND_VEGADRESSE
    } else if (matrikkeladresse != null) {
        AdresseMappingType.MATRIKKELADRESSE
    } else if (utenlandskAdresse != null) {
        AdresseMappingType.UTLAND_ADRESSE
    } else if (ukjentBosted != null) {
        AdresseMappingType.UKJENT_BOSTED
    } else {
        AdresseMappingType.EMPTY
    }

val Bostedsadresse.postnummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.postnummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.postnummer
        else -> null
    }

val Bostedsadresse.landkode: String?
    get() = when (mappingType) {
        AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse?.landkode
        else -> null
    }

val Bostedsadresse.kommunenummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.kommunenummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.kommunenummer
        AdresseMappingType.UKJENT_BOSTED -> ukjentBosted?.bostedskommune
        else -> null
    }
