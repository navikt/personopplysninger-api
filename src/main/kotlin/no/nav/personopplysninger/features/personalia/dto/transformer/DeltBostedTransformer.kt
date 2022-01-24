package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.DeltBosted
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlDeltBosted
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DeltBostedTransformer {

    private val logger: Logger = LoggerFactory.getLogger(DeltBostedTransformer::class.java)

    fun toOutbound(inbound: PdlDeltBosted, kodeverk: PersonaliaKodeverk): DeltBosted? {
        val adresse = transformAdresse(inbound, kodeverk)
        return if (adresse != null) {
            DeltBosted(
                startdatoForKontrakt = inbound.startdatoForKontrakt,
                sluttdatoForKontrakt = inbound.sluttdatoForKontrakt,
                coAdressenavn = inbound.coAdressenavn,
                kilde = inbound.metadata.master.lowercase(),
                adresse = adresse
            )
        } else {
            null
        }
    }

    private fun transformAdresse(inbound: PdlDeltBosted, kodeverk: PersonaliaKodeverk): Adresse? {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(
                inbound.vegadresse!!,
                kodeverk.deltBostedPostSted,
                kodeverk.deltBostedKommune
            )
            MATRIKKELADRESSE -> transformMatrikkeladresse(
                inbound.matrikkeladresse!!,
                kodeverk.deltBostedPostSted,
                kodeverk.deltBostedKommune
            )
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.deltBostedLand)
            UKJENT_BOSTED -> transformUkjentBosted(kodeverk.deltBostedKommune)
            else -> {
                logger.warn("Forsøkte å mappe deltbosted på uventet format, null returnert. Adressetype: ${inbound.mappingType}")
                null
            }
        }
    }

}
