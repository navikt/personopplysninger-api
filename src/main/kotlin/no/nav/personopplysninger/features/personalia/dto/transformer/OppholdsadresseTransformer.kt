package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Oppholdsadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlOppholdsadresse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OppholdsadresseTransformer {

    private val logger: Logger = LoggerFactory.getLogger(OppholdsadresseTransformer::class.java)

    fun toOutbound(inbound: PdlOppholdsadresse, kodeverk: PersonaliaKodeverk): Oppholdsadresse? {
        val adresse = transformAdresse(inbound, kodeverk)
        return if (adresse != null) {
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

    private fun transformAdresse(inbound: PdlOppholdsadresse, kodeverk: PersonaliaKodeverk): Adresse? {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(
                inbound.vegadresse!!,
                kodeverk.oppholdsadressePostSted,
                kodeverk.oppholdsadresseKommune
            )
            MATRIKKELADRESSE -> transformMatrikkeladresse(
                inbound.matrikkeladresse!!,
                kodeverk.oppholdsadressePostSted,
                kodeverk.oppholdsadresseKommune
            )
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.oppholdsadresseLand)
            else -> {
                logger.warn("Forsøkte å mappe oppholdsadresse på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                null
            }
        }
    }

}
