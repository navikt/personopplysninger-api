package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlGeografiskTilknytning


object GeografiskTilknytningTransformer {
    fun toOutbound(inbound: no.nav.tps.person.GeografiskTilknytning, kodeverk: PersonaliaKodeverk): GeografiskTilknytning = GeografiskTilknytning(
            bydel = inbound.bydel,
            datoFraOgMed = inbound.datoFraOgMed,
            kommune = inbound.kommune,
            land = inbound.land?.let { kodeverk.landterm }
    )

    fun toOutboundMigrert(inbound: PdlGeografiskTilknytning, kodeverk: PersonaliaKodeverk): GeografiskTilknytning = GeografiskTilknytning(
            bydel = inbound.gtBydel,
            kommune = inbound.gtKommune,
            land = kodeverk.gtLandterm
    )

}