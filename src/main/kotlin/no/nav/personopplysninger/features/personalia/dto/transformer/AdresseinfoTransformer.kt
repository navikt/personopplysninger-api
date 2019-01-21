package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.tps.person.Adresseinfo

object AdresseinfoTransformer {
    fun toOutbound(inbound: Adresseinfo): Adresser {

        return Adresser(
                boadresse = inbound.boadresse?.let { BoadresseTransformer.toOutbound(it) },
                prioritertAdresse = inbound.prioritertAdresse?.let { it.kode?.verdi },
                geografiskTilknytning = inbound.geografiskTilknytning?.let { GeografiskTilknytningTransformer.toOutbound(it) },
                tilleggsadresse = inbound.tilleggsadresse?.let { TilleggsadresseTransformer.toOutbound(it) },
                postadresse = inbound.postadresse?.let { PostadresseTransformer.toOutbound(it) },
                utenlandskAdresse = inbound.utenlandskAdresse?.let { UtenlandskAdresseTransformer.toOutbound(it) }
        )
    }

}