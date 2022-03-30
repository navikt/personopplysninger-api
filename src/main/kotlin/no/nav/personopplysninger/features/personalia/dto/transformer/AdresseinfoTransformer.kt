package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.util.firstOrNull

object AdresseinfoTransformer {

    fun toOutbound(pdlData: PdlData, kodeverk: PersonaliaKodeverk): Adresser {

        val kontaktadresse = pdlData.person?.kontaktadresse
        val bostedsadresse = pdlData.person?.bostedsadresse?.firstOrNull()
        val oppholdsadresse = pdlData.person?.oppholdsadresse
        val deltBosted = pdlData.person?.deltBosted?.firstOrNull()

        val kontaktadresseKodeverk = kodeverk.kontaktadresseKodeverk
        val bostedsadresseKodeverk = kodeverk.bostedsadresseKodeverk
        val oppholdsadresseKodeverk = kodeverk.oppholdsadresseKodeverk
        val deltBostedKodeverk = kodeverk.deltBostedKodeverk

        return Adresser(
            geografiskTilknytning = pdlData.geografiskTilknytning?.let {
                GeografiskTilknytningTransformer.toOutbound(
                    it,
                    kodeverk
                )
            },
            kontaktadresser = kontaktadresse!!.zip(kontaktadresseKodeverk)
                .mapNotNull { pair -> KontaktadresseTransformer.toOutbound(pair.first, pair.second) },
            bostedsadresse = bostedsadresse?.let { BostedsadresseTransformer.toOutbound(it, bostedsadresseKodeverk) },
            oppholdsadresser = oppholdsadresse!!.zip(oppholdsadresseKodeverk)
                .mapNotNull { pair -> OppholdsadresseTransformer.toOutbound(pair.first, pair.second) },
            deltBosted = deltBosted?.let { DeltBostedTransformer.toOutbound(it, deltBostedKodeverk) },
        )
    }
}