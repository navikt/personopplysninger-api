package no.nav.personopplysninger.features.personalia.pdl.dto

import no.nav.personopplysninger.features.personalia.pdl.dto.identer.PdlIdentInformasjon

data class PdlIdentliste(
    val identer: List<PdlIdentInformasjon> = emptyList(),
)