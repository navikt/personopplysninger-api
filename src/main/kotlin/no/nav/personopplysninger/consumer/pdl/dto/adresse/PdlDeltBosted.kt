package no.nav.personopplysninger.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import no.nav.personopplysninger.consumer.pdl.dto.common.PdlMetadata
import java.time.LocalDate

@Serializable
data class PdlDeltBosted(
    @Serializable(with = LocalDateSerializer::class)
    val startdatoForKontrakt: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    val sluttdatoForKontrakt: LocalDate? = null,
    val coAdressenavn: String? = null,
    val vegadresse: PdlVegadresse? = null,
    val matrikkeladresse: PdlMatrikkeladresse? = null,
    val utenlandskAdresse: PdlUtenlandskAdresse? = null,
    val ukjentBosted: PdlUkjentbosted? = null,
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

    val kommunenummer: String?
        get() {
            return when (mappingType) {
                AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse!!.kommunenummer
                AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse!!.kommunenummer
                AdresseMappingType.UKJENT_BOSTED -> ukjentBosted!!.bostedskommune
                else -> null
            }
        }
}