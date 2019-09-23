package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.tps.person.Telefoninfo

object TelefoninfoTransformer {
    fun toOutbound(inbound: Telefoninfo): Tlfnr {

        return Tlfnr(
                jobb = inbound.jobb,
                landkodeJobb = inbound.landkodeJobb,
                mobil = inbound.mobil,
                landkodeMobil = inbound.landkodeMobil,
                privat = inbound.privat,
                landkodePrivat = inbound.landkodePrivat)
    }
}