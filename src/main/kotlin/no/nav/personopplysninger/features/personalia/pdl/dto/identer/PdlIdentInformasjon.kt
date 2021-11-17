package no.nav.personopplysninger.features.personalia.pdl.dto.identer

data class PdlIdentInformasjon(
    val ident: String,
    val gruppe: PdlIdentGruppe,
    val historisk: Boolean,
)