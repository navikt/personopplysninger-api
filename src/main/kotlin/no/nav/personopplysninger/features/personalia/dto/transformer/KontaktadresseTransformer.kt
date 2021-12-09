package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.AdresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse

object KontaktadresseTransformer {

    fun toOutbound(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse {
        return Kontaktadresse(
            gyldigFraOgMed = inbound.gyldigFraOgMed,
            gyldigTilOgMed = inbound.gyldigTilOgMed,
            coAdressenavn = inbound.coAdressenavn,
            kilde = inbound.metadata.master.lowercase(),
            adresse = transformAdresse(inbound, kodeverk)
        )
    }

    private fun transformAdresse(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Adresse {
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
            else -> throw IllegalStateException("Prøvde å transformere ugyldig PdlKontaktadresse-objekt.")
        }
    }
}