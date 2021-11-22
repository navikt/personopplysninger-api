package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

import java.time.LocalDate
import java.time.LocalDateTime

data class PdlBostedsadresse(
    val angittFlyttedato: LocalDate?,
    val gyldigFraOgMed: LocalDateTime?,
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val vegadresse: PdlVegadresse?,
    val matrikkeladresse: PdlMatrikkeladresse?,
    val utenlandskAdresse: PdlUtenlandskAdresse?,
    val ukjentBosted: PdlUkjentbosted?,
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