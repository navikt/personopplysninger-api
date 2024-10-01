package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.INNLAND_FRIFORMADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.INNLAND_POSTBOKSADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.INNLAND_VEGADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.UTLAND_ADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.UTLAND_FRIFORMADRESSE
import no.nav.personopplysninger.personalia.extensions.mappingType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import no.nav.pdl.generated.dto.hentpersonquery.Kontaktadresse as PdlKontaktadresse

private val logger: Logger = LoggerFactory.getLogger("KontaktadresseTransformer")

fun PdlKontaktadresse.toOutbound(kodeverk: AdresseKodeverk): Kontaktadresse? {
    return this.transformAdresse(kodeverk)?.let {
        Kontaktadresse(
            gyldigTilOgMed = gyldigTilOgMed,
            coAdressenavn = coAdressenavn,
            kilde = metadata.master.lowercase(),
            adresse = it
        )
    }
}

private fun PdlKontaktadresse.transformAdresse(kodeverk: AdresseKodeverk): Adresse? {
    return when (mappingType) {
        INNLAND_VEGADRESSE -> requireNotNull(vegadresse).toOutbound(kodeverk.poststed, kodeverk.kommune)
        INNLAND_FRIFORMADRESSE -> requireNotNull(postadresseIFrittFormat).toOutbound(kodeverk.poststed)
        INNLAND_POSTBOKSADRESSE -> requireNotNull(postboksadresse).toOutbound(kodeverk.poststed)
        UTLAND_ADRESSE -> requireNotNull(utenlandskAdresse).toOutbound(kodeverk.land)
        UTLAND_FRIFORMADRESSE -> requireNotNull(utenlandskAdresseIFrittFormat).toOutbound(kodeverk.land)
        else -> {
            logger.warn("Forsøkte å mappe oppholdsadresse på uventet format, null returnert. Adressetype: $mappingType")
            null
        }
    }
}