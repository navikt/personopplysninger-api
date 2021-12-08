package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

import no.nav.personopplysninger.features.personalia.pdl.dto.common.PdlMetadata
import java.time.LocalDate

data class PdlDeltBosted(
    val startdatoForKontrakt: LocalDate?,
    val sluttdatoForKontrakt: LocalDate?,
    val coAdressenavn: String?,
    val vegadresse: PdlVegadresse?,
    val matrikkeladresse: PdlMatrikkeladresse?,
    val utenlandskAdresse: PdlUtenlandskAdresse?,
    val ukjentBosted: PdlUkjentbosted?,
    val metadata: PdlMetadata
) {
    val mappingType: AdresseMappingType
        get() {
            return if (vegadresse != null) {
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
        }

    val postnummer: String?
        get() {
            return when (mappingType) {
                AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse!!.postnummer
                AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse!!.postnummer
                else -> null
            }
        }

    val landkode: String?
        get() {
            return when (mappingType) {
                AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse!!.landkode
                else -> null
            }

        }
}