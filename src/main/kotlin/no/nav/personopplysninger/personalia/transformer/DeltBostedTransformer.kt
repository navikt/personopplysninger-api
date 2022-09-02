package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.common.pdl.dto.adresse.AdresseMappingType.INNLAND_VEGADRESSE
import no.nav.personopplysninger.common.pdl.dto.adresse.AdresseMappingType.MATRIKKELADRESSE
import no.nav.personopplysninger.common.pdl.dto.adresse.AdresseMappingType.UKJENT_BOSTED
import no.nav.personopplysninger.common.pdl.dto.adresse.AdresseMappingType.UTLAND_ADRESSE
import no.nav.personopplysninger.common.pdl.dto.adresse.PdlDeltBosted
import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.DeltBosted
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DeltBostedTransformer {

    private val logger: Logger = LoggerFactory.getLogger(DeltBostedTransformer::class.java)

    fun toOutbound(inbound: PdlDeltBosted, kodeverk: AdresseKodeverk): DeltBosted? {
        val adresse = transformAdresse(inbound, kodeverk)
        return if (adresse != null) {
            DeltBosted(
                startdatoForKontrakt = inbound.startdatoForKontrakt,
                sluttdatoForKontrakt = inbound.sluttdatoForKontrakt,
                coAdressenavn = inbound.coAdressenavn,
                kilde = inbound.metadata.master?.lowercase(),
                adresse = adresse
            )
        } else {
            null
        }
    }

    private fun transformAdresse(inbound: PdlDeltBosted, kodeverk: AdresseKodeverk): Adresse? {
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
                logger.warn("Forsøkte å mappe deltbosted på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                null
            }
        }
    }

}
