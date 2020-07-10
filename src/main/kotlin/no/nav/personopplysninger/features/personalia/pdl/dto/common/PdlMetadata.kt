package no.nav.personopplysninger.features.personalia.pdl.dto.common

data class PdlMetadata(
        val opplysningsId: String?,
        val master: String,
        val endringer: List<PdlEndring>,
        val historisk: Boolean
)