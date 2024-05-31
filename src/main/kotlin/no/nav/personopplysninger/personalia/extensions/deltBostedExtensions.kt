package no.nav.personopplysninger.personalia.extensions

import no.nav.pdl.generated.dto.hentpersonquery.DeltBosted
import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.AdresseMappingType

val DeltBosted.mappingType: AdresseMappingType
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

val DeltBosted.postnummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.postnummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.postnummer
        else -> null
    }

val DeltBosted.landkode: String?
    get() = when (mappingType) {
        AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse?.landkode
        else -> null
    }

val DeltBosted.kommunenummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.kommunenummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.kommunenummer
        AdresseMappingType.UKJENT_BOSTED -> ukjentBosted?.bostedskommune
        else -> null
    }
