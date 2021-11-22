package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.features.personalia.dto.outbound.AdresserMigrert
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.util.firstOrNull
import no.nav.tps.person.Adresseinfo

object AdresseinfoTransformer {
    fun toOutbound(
        inbound: Adresseinfo?,
        kontaktadresser: List<PdlKontaktadresse>,
        kodeverk: PersonaliaKodeverk
    ): Adresser? {

        val kontaktadresse = kontaktadresser.firstOrNull()

        return if (inbound == null && kontaktadresse == null) {
            return null
        } else {
            Adresser(
                boadresse = inbound?.boadresse?.let { BoadresseTransformer.toOutbound(it, kodeverk) },
                geografiskTilknytning = inbound?.geografiskTilknytning?.let {
                    GeografiskTilknytningTransformer.toOutbound(
                        it,
                        kodeverk
                    )
                },
                kontaktadresse = kontaktadresse?.let { KontaktadresseTransformer.toOutbound(it, kodeverk) },
                postadresse = inbound?.postadresse?.let { PostadresseTransformer.toOutbound(it, kodeverk) }
            )
        }
    }

    fun toOutboundMigrert(pdlData: PdlData, kodeverk: PersonaliaKodeverk): AdresserMigrert {

        val kontaktadresse = pdlData.person?.kontaktadresse?.firstOrNull()
        val bostedsadresse = pdlData.person?.bostedsadresse?.firstOrNull()
        val oppholdsadresse = pdlData.person?.oppholdsadresse?.firstOrNull()
        val deltBosted = pdlData.person?.deltBosted?.firstOrNull()

        return AdresserMigrert (
            geografiskTilknytning = pdlData.geografiskTilknytning?.let {
                GeografiskTilknytningTransformer.toOutboundMigrert(
                    it,
                    kodeverk
                )
            },
            kontaktadresse = kontaktadresse?.let { KontaktadresseTransformerMigrert.toOutbound(it, kodeverk) },
            bostedsadresse = bostedsadresse?.let { BostedsadresseTransformer.toOutbound(it, kodeverk) },
            oppholdsadresse = oppholdsadresse?.let { OppholdsadresseTransformer.toOutbound(it, kodeverk) },
            deltBosted = deltBosted?.let { DeltBostedTransformer.toOutbound(it, kodeverk) },
        )
    }
}