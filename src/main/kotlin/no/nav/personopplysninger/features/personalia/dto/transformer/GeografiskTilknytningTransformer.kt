package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlGeografiskTilknytning


object GeografiskTilknytningTransformer {

    fun toOutbound(inbound: PdlGeografiskTilknytning, kodeverk: PersonaliaKodeverk): GeografiskTilknytning = GeografiskTilknytning(
            bydel = inbound.gtBydel,
            kommune = inbound.gtKommune,
            land = kodeverk.gtLandterm
    )

}