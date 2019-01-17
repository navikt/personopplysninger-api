package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning


object GeografiskTilknytningTransformer {
    fun toOutbound(inbound: no.nav.tps.person.GeografiskTilknytning): GeografiskTilknytning = GeografiskTilknytning(
            bydel = inbound.bydel,
            datoFraOgMed = inbound.datoFraOgMed,
            kommune = inbound.kommune,
            land = inbound.land
    )

}
