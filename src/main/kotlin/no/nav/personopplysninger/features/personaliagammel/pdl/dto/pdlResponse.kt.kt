package no.nav.personopplysninger.features.personaliagammel.pdl.dto

data class PdlResponse(
        val data: PdlData
)

data class PdlData (
        val person: PdlPersonInfo
)