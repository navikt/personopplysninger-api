package no.nav.personopplysninger.consumer.pdl.dto.adresse

import no.nav.personopplysninger.consumer.pdl.dto.common.PdlMetadata
import java.time.LocalDateTime

data class PdlKontaktadresse(
    val gyldigFraOgMed: LocalDateTime?,
    val gyldigTilOgMed: LocalDateTime?,
    val type: PdlKontaktadressetype,
    val coAdressenavn: String?,
    val postboksadresse: PdlPostboksadresse?,
    val vegadresse: PdlVegadresse?,
    val postadresseIFrittFormat: PdlPostadresseIFrittFormat?,
    val utenlandskAdresse: PdlUtenlandskAdresse?,
    val utenlandskAdresseIFrittFormat: PdlUtenlandskAdresseIFrittFormat?,
    val folkeregistermetadata: PdlFolkeregistermetadata?,
    val metadata: PdlMetadata
) {
    val mappingType: AdresseMappingType
        get() {
            return if (vegadresse != null) {
                AdresseMappingType.INNLAND_VEGADRESSE
            } else if (postadresseIFrittFormat != null) {
                AdresseMappingType.INNLAND_FRIFORMADRESSE
            } else if (postboksadresse != null) {
                AdresseMappingType.INNLAND_POSTBOKSADRESSE
            } else if (utenlandskAdresse != null) {
                AdresseMappingType.UTLAND_ADRESSE
            } else if (utenlandskAdresseIFrittFormat != null) {
                AdresseMappingType.UTLAND_FRIFORMADRESSE
            } else {
                AdresseMappingType.EMPTY
            }
        }

    val postnummer: String?
        get() {
            return when (mappingType) {
                AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse!!.postnummer
                AdresseMappingType.INNLAND_FRIFORMADRESSE -> postadresseIFrittFormat!!.postnummer
                AdresseMappingType.INNLAND_POSTBOKSADRESSE -> postboksadresse!!.postnummer
                else -> null
            }
        }

    val landkode: String?
        get() {
            return when (mappingType) {
                AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse!!.landkode
                AdresseMappingType.UTLAND_FRIFORMADRESSE -> utenlandskAdresseIFrittFormat!!.landkode
                else -> null
            }
        }

    val kommunenummer: String?
        get() {
            return when (mappingType) {
                AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse!!.kommunenummer
                else -> null
            }
        }
}