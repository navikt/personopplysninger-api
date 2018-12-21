package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Kilde
import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.tps.person.Telefoninfo

object TelefoninfoTransformer {
    fun toOutbound(inbound: Telefoninfo): Tlfnr {
        val kilder: MutableSet<Kilde> = mutableSetOf()

        fun addKilde(kilde: String?): Unit {
            kilde?.let { kilder.add(Kilde(it)) }
        }

        return Tlfnr(
                jobb = inbound.jobb?.let {
                    addKilde(inbound.jobbKilde)
                    it
                },
                mobil = inbound.mobil?.let {
                    addKilde(inbound.mobilKilde)
                    it
                },
                privat = inbound.privat?.let {
                    addKilde(inbound.privatKilde)
                    it
                },
                datakilder = kilder)
    }
}