package no.nav.personopplysninger.features.personalia.pdl.dto

import no.nav.personopplysninger.features.personalia.pdl.dto.geografisktilkytning.PdlGtType

data class PdlGeografiskTilknytning(
    val gtType: PdlGtType,
    val gtKommune: String?,
    val gtBydel: String?,
    val gtLand: String?,
    val regel: String
)