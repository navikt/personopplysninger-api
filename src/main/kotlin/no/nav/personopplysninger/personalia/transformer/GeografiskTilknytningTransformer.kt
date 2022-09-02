package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.common.pdl.dto.PdlGeografiskTilknytning
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.GeografiskTilknytning


object GeografiskTilknytningTransformer {

    fun toOutbound(inbound: PdlGeografiskTilknytning, kodeverk: PersonaliaKodeverk): GeografiskTilknytning = GeografiskTilknytning(
            bydel = inbound.gtBydel,
            kommune = inbound.gtKommune,
            land = kodeverk.gtLandterm
    )

}