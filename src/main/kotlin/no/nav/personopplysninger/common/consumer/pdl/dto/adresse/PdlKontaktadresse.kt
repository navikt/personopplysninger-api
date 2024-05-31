package no.nav.personopplysninger.common.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.consumer.pdl.dto.common.PdlMetadata
import no.nav.personopplysninger.common.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime


//todo: Må lage extension functions for å hente postnummer osv

@Serializable
data class PdlKontaktadresse(
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigFraOgMed: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val gyldigTilOgMed: LocalDateTime? = null,
    val type: PdlKontaktadressetype,
    val coAdressenavn: String? = null,
    val postboksadresse: PdlPostboksadresse? = null,
    val vegadresse: PdlVegadresse? = null,
    val postadresseIFrittFormat: PdlPostadresseIFrittFormat? = null,
    val utenlandskAdresse: PdlUtenlandskAdresse? = null,
    val utenlandskAdresseIFrittFormat: PdlUtenlandskAdresseIFrittFormat? = null,
    val folkeregistermetadata: PdlFolkeregistermetadata? = null,
    val metadata: PdlMetadata
) {
    val mappingType = if (vegadresse != null) {
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

    val postnummer = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.postnummer
        AdresseMappingType.INNLAND_FRIFORMADRESSE -> postadresseIFrittFormat?.postnummer
        AdresseMappingType.INNLAND_POSTBOKSADRESSE -> postboksadresse?.postnummer
        else -> null
    }

    val landkode = when (mappingType) {
        AdresseMappingType.UTLAND_ADRESSE -> utenlandskAdresse?.landkode
        AdresseMappingType.UTLAND_FRIFORMADRESSE -> utenlandskAdresseIFrittFormat?.landkode
        else -> null
    }

    val kommunenummer = when (mappingType) {
        AdresseMappingType.INNLAND_VEGADRESSE -> vegadresse?.kommunenummer
        else -> null
    }
}
