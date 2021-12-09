package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Oppholdsadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlOppholdsadresse

object OppholdsadresseTransformer {

    fun toOutbound(inbound: PdlOppholdsadresse, kodeverk: PersonaliaKodeverk): Oppholdsadresse {
        return Oppholdsadresse(
            oppholdAnnetSted = inbound.oppholdAnnetSted,
            gyldigFraOgMed = inbound.gyldigFraOgMed,
            gyldigTilOgMed = inbound.gyldigTilOgMed,
            coAdressenavn = inbound.coAdressenavn,
            kilde = inbound.metadata.master.lowercase(),
            adresse = transformAdresse(inbound, kodeverk)
        )
    }

    private fun transformAdresse(inbound: PdlOppholdsadresse, kodeverk: PersonaliaKodeverk): Adresse {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformVegadresse(inbound.vegadresse!!, kodeverk.oppholdsadressePostSted)
            MATRIKKELADRESSE -> transformMatrikkeladresse(inbound.matrikkeladresse!!, kodeverk.oppholdsadressePostSted)
            UTLAND_ADRESSE -> transformUtenlandskAdresse(inbound.utenlandskAdresse!!, kodeverk.oppholdsadresseLand)
            else -> throw IllegalStateException("Prøvde å transformere ugyldig PdlOppholdsadresse-objekt.")
        }
    }

}
