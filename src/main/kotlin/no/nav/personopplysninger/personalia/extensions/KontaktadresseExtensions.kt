package no.nav.personopplysninger.personalia.extensions

import no.nav.pdl.generated.dto.hentpersonquery.Kontaktadresse
import no.nav.personopplysninger.personalia.enums.AdresseMappingType

val Kontaktadresse.mappingType: AdresseMappingType
    get() = when {
        vegadresse != null -> AdresseMappingType.INNLAND_VEGADRESSE
        postadresseIFrittFormat != null -> AdresseMappingType.INNLAND_FRIFORMADRESSE
        postboksadresse != null -> AdresseMappingType.INNLAND_POSTBOKSADRESSE
        utenlandskAdresse != null -> AdresseMappingType.UTLAND_ADRESSE
        utenlandskAdresseIFrittFormat != null -> AdresseMappingType.UTLAND_FRIFORMADRESSE
        else -> AdresseMappingType.EMPTY
    }

val Kontaktadresse.postnummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.postnummer
        AdresseMappingType.INNLAND_FRIFORMADRESSE -> postadresseIFrittFormat?.postnummer
        AdresseMappingType.INNLAND_POSTBOKSADRESSE -> postboksadresse?.postnummer
        else -> null
    }

val Kontaktadresse.landkode: String?
    get() = when (mappingType) {
        AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse?.landkode
        AdresseMappingType.UTLAND_FRIFORMADRESSE -> utenlandskAdresseIFrittFormat?.landkode
        else -> null
    }

val Kontaktadresse.kommunenummer: String?
    get() = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.kommunenummer
        else -> null
    }
