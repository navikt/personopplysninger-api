package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Bostedsadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlBostedsadresse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BostedsadresseTransformer {

    private val logger: Logger = LoggerFactory.getLogger(BostedsadresseTransformer::class.java)

    fun toOutbound(inbound: PdlBostedsadresse, kodeverk: PersonaliaKodeverk): Bostedsadresse? {
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

    private fun transformAdresse(inbound: PdlBostedsadresse, kodeverk: PersonaliaKodeverk): Adresse? {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(
                inbound.vegadresse!!,
                kodeverk.bostedsadressePostSted,
                kodeverk.bostedsadresseKommune
            )
            MATRIKKELADRESSE -> transformMatrikkeladresse(
                inbound.matrikkeladresse!!,
                kodeverk.bostedsadressePostSted,
                kodeverk.bostedsadresseKommune
            )
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.bostedsadresseLand)
            UKJENT_BOSTED -> transformUkjentBosted(kodeverk.bostedsadresseKommune)
            else -> {
                logger.warn("Forsøkte å mappe bostedsadresse på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                null
            }
        }
    }

}
