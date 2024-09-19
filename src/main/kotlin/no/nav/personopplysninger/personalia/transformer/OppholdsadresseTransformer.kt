package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Oppholdsadresse
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.INNLAND_VEGADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.enums.AdresseMappingType.UTLAND_ADRESSE
import no.nav.personopplysninger.personalia.extensions.mappingType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import no.nav.pdl.generated.dto.hentpersonquery.Oppholdsadresse as PdlOppholdsadresse

private val logger: Logger = LoggerFactory.getLogger("OppholdsadresseTransformer")

fun PdlOppholdsadresse.toOutbound(kodeverk: AdresseKodeverk): Oppholdsadresse? {
    val adresse = transformAdresse(kodeverk)

    if (adresse == null && oppholdAnnetSted == null) return null

    return Oppholdsadresse(
        gyldigTilOgMed = gyldigTilOgMed,
        coAdressenavn = coAdressenavn,
        kilde = metadata.master.lowercase(),
        adresse = adresse
    )
}

private fun PdlOppholdsadresse.transformAdresse(kodeverk: AdresseKodeverk): Adresse? {
    return when (mappingType) {
        INNLAND_VEGADRESSE -> vegadresse.transformVegadresse(kodeverk.poststed, kodeverk.kommune)
        MATRIKKELADRESSE -> matrikkeladresse.transformMatrikkeladresse(kodeverk.poststed, kodeverk.kommune)
        UTLAND_ADRESSE -> utenlandskAdresse.transformUtenlandskAdresse(kodeverk.land)
        else -> {
            // Adresse kan være null dersom oppholdAnnetSted er satt. Da trenger vi ikke logge warning.
            if (oppholdAnnetSted == null) {
                logger.warn("Forsøkte å mappe oppholdsadresse på uventet format, null returnert. Adressetype: $mappingType")
            }
            null
        }
    }
}