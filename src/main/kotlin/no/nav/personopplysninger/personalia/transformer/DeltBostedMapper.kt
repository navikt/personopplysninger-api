package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.DeltBosted
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Ukjentbosted
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.INNLAND_VEGADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.UKJENT_BOSTED
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.UTLAND_ADRESSE
import no.nav.personopplysninger.personalia.extensions.mappingType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import no.nav.pdl.generated.dto.hentpersonquery.DeltBosted as PdlDeltBosted

private val logger: Logger = LoggerFactory.getLogger("DeltBostedTransformer")

fun PdlDeltBosted.toOutbound(kodeverk: AdresseKodeverk): DeltBosted? {
    return this.transformAdresse(kodeverk)?.let { adresse ->
        DeltBosted(
            coAdressenavn = coAdressenavn,
            adresse = adresse
        )
    }
}

private fun PdlDeltBosted.transformAdresse(kodeverk: AdresseKodeverk): Adresse? {
    return when (mappingType) {
        INNLAND_VEGADRESSE -> vegadresse.toOutbound(kodeverk.poststed, kodeverk.kommune)
        MATRIKKELADRESSE -> matrikkeladresse.toOutbound(kodeverk.poststed, kodeverk.kommune)
        UTLAND_ADRESSE -> utenlandskAdresse.toOutbound(kodeverk.land)
        UKJENT_BOSTED -> Ukjentbosted(kodeverk.kommune)
        else -> {
            logger.warn("Forsøkte å mappe deltbosted på uventet format, null returnert. Adressetype: $mappingType")
            null
        }
    }
}
