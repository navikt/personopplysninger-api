package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.*
import no.nav.tps.person.Adresseinfo

object AdresseinfoTransformer {
    fun toOutbound(inbound: Adresseinfo): Adresser {
        val kilder: MutableSet<Kilde> = mutableSetOf()


        fun addKilde(kilde: String?): Unit {
            kilde?.let { kilder.add(Kilde(it)) }
        }


        fun geografiskTilknytning(inbound: no.nav.tps.person.GeografiskTilknytning): GeografiskTilknytning {
            addKilde(inbound.kilde)
            return GeografiskTilknytningTransformer.toOutbound(inbound)
        }

        fun postadr(inbound: no.nav.tps.person.Postadresse): Postadresse {
            addKilde(inbound.kilde)
            return PostadresseTransformer.toOutbound(inbound)
        }

        fun utenlandskAdr(inbound: no.nav.tps.person.UtenlandskAdresse): UtenlandskAdresse {
            addKilde(inbound.kilde)
            return UtenlandskAdresseTransformer.toOutbound(inbound)
        }

        fun tilleggsadr(inbound: no.nav.tps.person.Tilleggsadresse): Tilleggsadresse {
            addKilde(inbound.kilde)
            return TilleggsadresseTransformer.toOutbound(inbound)
        }

        return Adresser(
                boadresse = inbound.boadresse?.let { BoadresseTransformer.toOutbound(it) },
                prioritertAdresse = inbound.prioritertAdresse?.let { it.kode?.verdi }, // TODO Are: Kodeverk. Husk kilde.
                geografiskTilknytning = inbound.geografiskTilknytning?.let { geografiskTilknytning(it) },
                tilleggsadresse = inbound.tilleggsadresse?.let { tilleggsadr(it) },
                postadresse = inbound.postadresse?.let { postadr(it) },
                utenlandskAdresse = inbound.utenlandskAdresse?.let { utenlandskAdr(it) },
                datakilder = kilder
        )
    }

}