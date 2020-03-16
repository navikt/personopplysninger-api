package no.nav.personopplysninger.features.personalia.pdl.dto

data class PdlResponse(
        val data: PdlData
)

data class PdlData (
        val hentPerson: PdlPersonInfo
)