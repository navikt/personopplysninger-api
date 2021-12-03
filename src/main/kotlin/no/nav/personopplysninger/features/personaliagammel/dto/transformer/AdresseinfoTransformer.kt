package no.nav.personopplysninger.features.personaliagammel.dto.transformer

import no.nav.personopplysninger.features.personaliagammel.dto.outbound.Adresser
import no.nav.personopplysninger.features.personaliagammel.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.util.firstOrNull
import no.nav.tps.person.Adresseinfo

object AdresseinfoTransformer {
    fun toOutbound(inbound: Adresseinfo?, kontaktadresser: List<PdlKontaktadresse>, kodeverk: PersonaliaKodeverk): Adresser? {

        val kontaktadresse = kontaktadresser.firstOrNull()

        return if (inbound == null && kontaktadresse == null) {
            return null
        } else {
            Adresser(
                    boadresse = inbound?.boadresse?.let { BoadresseTransformer.toOutbound(it, kodeverk) },
                    prioritertAdresse = inbound?.prioritertAdresse?.let { it.kode?.verdi },
                    geografiskTilknytning = inbound?.geografiskTilknytning?.let { GeografiskTilknytningTransformer.toOutbound(it, kodeverk) },
                    kontaktadresse = kontaktadresse?.let { KontaktadresseTranformer.toOutbound(it, kodeverk) },
                    postadresse = inbound?.postadresse?.let { PostadresseTransformer.toOutbound(it, kodeverk)}
            )
        }
    }

}