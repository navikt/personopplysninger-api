package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.DeltBosted
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlDeltBosted

object DeltBostedTransformer {

    fun toOutbound(inbound: PdlDeltBosted, kodeverk: PersonaliaKodeverk): DeltBosted {
        return DeltBosted(
            startdatoForKontrakt = inbound.startdatoForKontrakt,
            sluttdatoForKontrakt = inbound.sluttdatoForKontrakt,
            coAdressenavn = inbound.coAdressenavn,
            kilde = inbound.metadata.master.lowercase(),
            adresse = transformAdresse(inbound, kodeverk)
        )
    }

    private fun transformAdresse(inbound: PdlDeltBosted, kodeverk: PersonaliaKodeverk): Adresse {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(inbound.vegadresse!!, kodeverk.deltBostedPostSted)
            MATRIKKELADRESSE -> transformMatrikkeladresse(inbound.matrikkeladresse!!, kodeverk.deltBostedPostSted)
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.deltBostedLand)
            UKJENT_BOSTED -> transformUkjentBosted(inbound.ukjentBosted!!)
            else -> throw IllegalStateException("Prøvde å transformere ugyldig PdlDeltBosted-objekt.")
        }
    }

}
