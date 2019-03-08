package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.Adresseinfo

object AdresseinfoTransformer {
    fun toOutbound(inbound: Adresseinfo, kodeverk: PersonaliaKodeverk): Adresser {

        return Adresser(
                boadresse = inbound.boadresse?.let { BoadresseTransformer.toOutbound(it, kodeverk) },
                prioritertAdresse = inbound.prioritertAdresse?.let { it.kode?.verdi },
                geografiskTilknytning = inbound.geografiskTilknytning?.let { GeografiskTilknytningTransformer.toOutbound(it, kodeverk) },
                tilleggsadresse = inbound.tilleggsadresse?.let { TilleggsadresseTransformer.toOutbound(it, kodeverk) },
                postadresse = inbound.postadresse?.let { PostadresseTransformer.toOutbound(it, kodeverk)},
                utenlandskAdresse = inbound.utenlandskAdresse?.let { UtenlandskAdresseTransformer.toOutbound(it, kodeverk) }
        )
    }

}