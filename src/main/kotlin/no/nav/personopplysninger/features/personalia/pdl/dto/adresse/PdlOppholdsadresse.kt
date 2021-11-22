package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

import java.time.LocalDateTime

data class PdlOppholdsadresse(
    val gyldigFraOgMed: LocalDateTime?,
    val gyldigTilOgMed: LocalDateTime?,
    val coAdressenavn: String?,
    val utenlandskAdresse: PdlUtenlandskAdresse?,
    val vegadresse: PdlVegadresse?,
    val matrikkeladresse: PdlMatrikkeladresse?,
    val oppholdAnnetSted: String?,
) {
    val mappingType: AdresseMappingType
        get() {
            return if (vegadresse != null) {
                AdresseMappingType.INNLAND_VEGADRESSE
            } else if (matrikkeladresse != null) {
                AdresseMappingType.MATRIKKELADRESSE
            } else if (utenlandskAdresse != null) {
                AdresseMappingType.UTLAND_ADRESSE
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