package no.nav.personopplysninger.features.personaliagammel.pdl.dto.common

data class PdlMetadata(
        val opplysningsId: String?,
        val master: String,
        val endringer: List<PdlEndring>,
        val historisk: Boolean
)