package no.nav.personopplysninger.features.personaliagammel.dto.transformer

import no.nav.tps.person.Matrikkeladresse

object MatrikkeladresseTransformer {
    fun toOutbound(inbound: Matrikkeladresse) = no.nav.personopplysninger.features.personaliagammel.dto.outbound.Matrikkeladresse (
            bruksnummer = inbound.bruksnummer,
            festenummer = inbound.festenummer,
            gaardsnummer = inbound.gaardsnummer,
            undernummer = inbound.undernummer
    )
}