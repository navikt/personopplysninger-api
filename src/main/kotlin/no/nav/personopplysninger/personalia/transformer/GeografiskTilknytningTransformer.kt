package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.GeografiskTilknytning
import no.nav.pdl.generated.dto.hentpersonquery.GeografiskTilknytning as PdlGeografiskTilknytning

object GeografiskTilknytningTransformer {

    fun toOutbound(inbound: PdlGeografiskTilknytning, kodeverk: PersonaliaKodeverk): GeografiskTilknytning {
        return GeografiskTilknytning(
            bydel = inbound.gtBydel,
            kommune = inbound.gtKommune,
            land = kodeverk.gtLandterm
        )
    }
}