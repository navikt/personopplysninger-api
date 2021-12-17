package no.nav.personopplysninger.features.personaliagammel.dto.transformer

import no.nav.tps.person.Veiadresse

object VeiadresseTransformer {
    fun toOutbound(inbound: Veiadresse) = no.nav.personopplysninger.features.personaliagammel.dto.outbound.Veiadresse (
            bokstav = inbound.bokstav,
            bolignummer = inbound.bolignummer,
            gatekode = inbound.gatekode,
            husnummer = inbound.husnummer
    )
}