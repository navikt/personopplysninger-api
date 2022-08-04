package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.dto.PersonaliaKodeverk
import no.nav.personopplysninger.consumer.pdl.dto.PdlGeografiskTilknytning
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning


object GeografiskTilknytningTransformer {

    fun toOutbound(inbound: PdlGeografiskTilknytning, kodeverk: PersonaliaKodeverk): GeografiskTilknytning = GeografiskTilknytning(
            bydel = inbound.gtBydel,
            kommune = inbound.gtKommune,
            land = kodeverk.gtLandterm
    )

}