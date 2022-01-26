package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.features.personalia.kodeverk.AdresseKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object KontaktadresseTransformer {

    private val logger: Logger = LoggerFactory.getLogger(KontaktadresseTransformer::class.java)

    fun toOutbound(inbound: PdlKontaktadresse, kodeverk: AdresseKodeverk): Kontaktadresse? {
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

    private fun transformAdresse(inbound: PdlKontaktadresse, kodeverk: AdresseKodeverk): Adresse? {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(
                inbound.vegadresse!!,
                kodeverk.poststed,
                kodeverk.kommune
            )
            INNLAND_FRIFORMADRESSE -> transformPostadresseIFrittFormat(
                inbound.postadresseIFrittFormat!!,
                kodeverk.poststed
            )
            INNLAND_POSTBOKSADRESSE -> transformPostboksadresse(
                inbound.postboksadresse!!,
                kodeverk.poststed
            )
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.land)
            UTLAND_FRIFORMADRESSE -> transformUtenlandskAdresseIFrittFormat(
                inbound.utenlandskAdresseIFrittFormat!!,
                kodeverk.land
            )
            else -> {
                logger.warn("Forsøkte å mappe oppholdsadresse på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                null
            }
        }
    }
}