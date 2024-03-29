package no.nav.personopplysninger.common.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.consumer.pdl.dto.common.PdlMetadata
import no.nav.personopplysninger.common.serializer.LocalDateSerializer
import no.nav.personopplysninger.common.serializer.LocalDateTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class PdlBostedsadresse(
    @Serializable(with = LocalDateSerializer::class)
    val angittFlyttedato: LocalDate? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigFraOgMed: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigTilOgMed: LocalDateTime? = null,
    val coAdressenavn: String? = null,
    val vegadresse: PdlVegadresse? = null,
    val matrikkeladresse: PdlMatrikkeladresse? = null,
    val utenlandskAdresse: PdlUtenlandskAdresse? = null,
    val ukjentBosted: PdlUkjentbosted? = null,
    val metadata: PdlMetadata
) {
    val mappingType = if (vegadresse != null) {
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

    val postnummer = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.postnummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.postnummer
        else -> null
    }

    val landkode = when (mappingType) {
        AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse?.landkode
        else -> null
    }

    val kommunenummer = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.kommunenummer
        AdresseMappingType.MATRIKKELADRESSE -> matrikkeladresse?.kommunenummer
        AdresseMappingType.UKJENT_BOSTED -> ukjentBosted?.bostedskommune
        else -> null
    }
}