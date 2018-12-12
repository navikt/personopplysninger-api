package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.tps.person.Telefoninfo

object TelefoninfoTransformer {
    fun toOutbound(inbound: Telefoninfo) = no.nav.personopplysninger.features.personalia.dto.outbound.Telefoninfo(
            jobb = inbound.jobb,
            mobil = inbound.mobil,
            privat = inbound.privat
    )
}