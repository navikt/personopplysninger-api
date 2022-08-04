package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.dto.AdresseKodeverk
import no.nav.personopplysninger.consumer.pdl.dto.adresse.AdresseMappingType.INNLAND_VEGADRESSE
import no.nav.personopplysninger.consumer.pdl.dto.adresse.AdresseMappingType.MATRIKKELADRESSE
import no.nav.personopplysninger.consumer.pdl.dto.adresse.AdresseMappingType.UTLAND_ADRESSE
import no.nav.personopplysninger.consumer.pdl.dto.adresse.PdlOppholdsadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Oppholdsadresse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OppholdsadresseTransformer {

    private val logger: Logger = LoggerFactory.getLogger(OppholdsadresseTransformer::class.java)

    fun toOutbound(inbound: PdlOppholdsadresse, kodeverk: AdresseKodeverk): Oppholdsadresse? {
        val adresse = transformAdresse(inbound, kodeverk)
        return if (adresse != null || inbound.oppholdAnnetSted != null) {
            Oppholdsadresse(
                oppholdAnnetSted = inbound.oppholdAnnetSted,
                gyldigFraOgMed = inbound.gyldigFraOgMed,
                gyldigTilOgMed = inbound.gyldigTilOgMed,
                coAdressenavn = inbound.coAdressenavn,
                kilde = inbound.metadata.master.lowercase(),
                adresse = adresse
            )
        } else {
            null
        }
    }

    private fun transformAdresse(inbound: PdlOppholdsadresse, kodeverk: AdresseKodeverk): Adresse? {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(
                inbound.vegadresse!!,
                kodeverk.poststed,
                kodeverk.kommune
            )
            MATRIKKELADRESSE -> transformMatrikkeladresse(
                inbound.matrikkeladresse!!,
                kodeverk.poststed,
                kodeverk.kommune
            )
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.land)
            else -> {
                // Adresse kan være null dersom oppholdAnnetSted er satt. Da trenger vi ikke logge warning.
                if (inbound.oppholdAnnetSted == null) {
                    logger.warn("Forsøkte å mappe oppholdsadresse på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                }
                null
            }
        }
    }

}
