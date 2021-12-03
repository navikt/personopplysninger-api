package no.nav.personopplysninger.features.personaliagammel.dto.transformer

import no.nav.personopplysninger.features.personaliagammel.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personaliagammel.kodeverk.PersonaliaKodeverk


object GeografiskTilknytningTransformer {
    fun toOutbound(inbound: no.nav.tps.person.GeografiskTilknytning, kodeverk: PersonaliaKodeverk): GeografiskTilknytning = GeografiskTilknytning(
            bydel = inbound.bydel,
            datoFraOgMed = inbound.datoFraOgMed,
            kommune = inbound.kommune,
            land = inbound.land?.let { kodeverk.landterm }
    )

}