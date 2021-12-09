package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Bostedsadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlBostedsadresse

object BostedsadresseTransformer {

    fun toOutbound(inbound: PdlBostedsadresse, kodeverk: PersonaliaKodeverk): Bostedsadresse {
        return Bostedsadresse(
            angittFlyttedato = inbound.angittFlyttedato,
            gyldigFraOgMed = inbound.gyldigFraOgMed,
            gyldigTilOgMed = inbound.gyldigTilOgMed,
            coAdressenavn = inbound.coAdressenavn,
            kilde = inbound.metadata.master.lowercase(),
            adresse = transformAdresse(inbound, kodeverk)
        )
    }

    private fun transformAdresse(inbound: PdlBostedsadresse, kodeverk: PersonaliaKodeverk): Adresse {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(inbound.vegadresse!!, kodeverk.bostedsadressePostSted)
            MATRIKKELADRESSE -> transformMatrikkeladresse(inbound.matrikkeladresse!!, kodeverk.bostedsadressePostSted)
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.bostedsadresseLand)
            UKJENT_BOSTED -> transformUkjentBosted(inbound.ukjentBosted!!)
            else -> throw IllegalStateException("Prøvde å transformere ugyldig PdlBostedsadresse-objekt.")
        }
    }

}
