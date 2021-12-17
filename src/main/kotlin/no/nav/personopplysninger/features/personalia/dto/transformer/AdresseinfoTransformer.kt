package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.util.firstOrNull

object AdresseinfoTransformer {

    fun toOutbound(pdlData: PdlData, kodeverk: PersonaliaKodeverk): Adresser {

        val kontaktadresse = pdlData.person?.kontaktadresse
        val bostedsadresse = pdlData.person?.bostedsadresse?.firstOrNull()
        val oppholdsadresse = pdlData.person?.oppholdsadresse?.firstOrNull()
        val deltBosted = pdlData.person?.deltBosted?.firstOrNull()

        return Adresser (
            geografiskTilknytning = pdlData.geografiskTilknytning?.let {
                GeografiskTilknytningTransformer.toOutbound(
                    it,
                    kodeverk
                )
            },
            kontaktadresse = kontaktadresse!!.map { (KontaktadresseTransformer.toOutbound(it, kodeverk)) },
            bostedsadresse = bostedsadresse?.let { BostedsadresseTransformer.toOutbound(it, kodeverk) },
            oppholdsadresse = oppholdsadresse?.let { OppholdsadresseTransformer.toOutbound(it, kodeverk) },
            deltBosted = deltBosted?.let { DeltBostedTransformer.toOutbound(it, kodeverk) },
        )
    }
}