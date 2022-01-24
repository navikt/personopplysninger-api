package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object KontaktadresseTransformer {

    private val logger: Logger = LoggerFactory.getLogger(KontaktadresseTransformer::class.java)

    fun toOutbound(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse? {
        val adresse = transformAdresse(inbound, kodeverk)
        return if (adresse != null) {
            Kontaktadresse(
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

    private fun transformAdresse(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Adresse? {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(
                inbound.vegadresse!!,
                kodeverk.kontaktadressePostSted,
                kodeverk.kontaktadresseKommune
            )
            INNLAND_FRIFORMADRESSE -> transformPostadresseIFrittFormat(
                inbound.postadresseIFrittFormat!!,
                kodeverk.kontaktadressePostSted
            )
            INNLAND_POSTBOKSADRESSE -> transformPostboksadresse(
                inbound.postboksadresse!!,
                kodeverk.kontaktadressePostSted
            )
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.kontaktadresseLand)
            UTLAND_FRIFORMADRESSE -> transformUtenlandskAdresseIFrittFormat(
                inbound.utenlandskAdresseIFrittFormat!!,
                kodeverk.kontaktadresseLand
            )
            else -> {
                logger.warn("Forsøkte å mappe oppholdsadresse på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                null
            }
        }
    }
}