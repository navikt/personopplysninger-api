package no.nav.personopplysninger.features.personalia.pdl.dto.common

import no.nav.personopplysninger.features.personalia.pdl.dto.common.PdlEndring

data class PdlMetadata(
        val opplysningsId: String?,
        val master: String,
        val endringer: List<PdlEndring>,
        val historisk: Boolean
)