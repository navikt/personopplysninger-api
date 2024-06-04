package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.util.firstOrNull

object AdresseinfoTransformer {

    fun toOutbound(pdlData: HentPersonQuery.Result, kodeverk: PersonaliaKodeverk): Adresser {
        val kontaktadresse = pdlData.person!!.kontaktadresse
        val bostedsadresse = pdlData.person.bostedsadresse.firstOrNull()
        val oppholdsadresse = pdlData.person.oppholdsadresse
        val deltBosted = pdlData.person.deltBosted.firstOrNull()

        val kontaktadresseKodeverk = kodeverk.kontaktadresseKodeverk
        val bostedsadresseKodeverk = kodeverk.bostedsadresseKodeverk
        val oppholdsadresseKodeverk = kodeverk.oppholdsadresseKodeverk
        val deltBostedKodeverk = kodeverk.deltBostedKodeverk

        return Adresser(
            //todo: zip her??
            kontaktadresser = kontaktadresse.zip(kontaktadresseKodeverk)
                .mapNotNull { pair -> KontaktadresseTransformer.toOutbound(pair.first, pair.second) },
            bostedsadresse = bostedsadresse?.let { BostedsadresseTransformer.toOutbound(it, bostedsadresseKodeverk) },
            oppholdsadresser = oppholdsadresse.zip(oppholdsadresseKodeverk)
                .mapNotNull { pair -> OppholdsadresseTransformer.toOutbound(pair.first, pair.second) },
            deltBosted = deltBosted?.let { DeltBostedTransformer.toOutbound(it, deltBostedKodeverk) },
        )
    }
}