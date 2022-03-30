package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.domain.AdresseKodeverk
import no.nav.personopplysninger.consumer.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.consumer.pdl.dto.adresse.PdlBostedsadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Bostedsadresse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BostedsadresseTransformer {

    private val logger: Logger = LoggerFactory.getLogger(BostedsadresseTransformer::class.java)

    fun toOutbound(inbound: PdlBostedsadresse, kodeverk: AdresseKodeverk): Bostedsadresse? {
        val adresse = transformAdresse(inbound, kodeverk)
        return if (adresse != null) {
            Bostedsadresse(
                angittFlyttedato = inbound.angittFlyttedato,
                gyldigFraOgMed = inbound.gyldigFraOgMed,
                gyldigTilOgMed = inbound.gyldigTilOgMed,
                coAdressenavn = inbound.coAdressenavn,
                kilde = inbound.metadata.master.lowercase(),
                adresse = adresse
            )
        } else {
            null;
        }
    }

    private fun transformAdresse(inbound: PdlBostedsadresse, kodeverk: AdresseKodeverk): Adresse? {
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
            UKJENT_BOSTED -> transformUkjentBosted(kodeverk.kommune)
            else -> {
                logger.warn("Forsøkte å mappe bostedsadresse på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                null
            }
        }
    }

}
