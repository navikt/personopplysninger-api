package no.nav.personopplysninger.features.personaliagammel.pdl

import no.nav.personopplysninger.features.personaliagammel.pdl.dto.QueryVariables

interface PDLRequest {
    val variables: QueryVariables
    val query: String
}

fun String.compactJson(): String =
    trimIndent().replace("\r", " ").replace("\n", " ").replace("\\s+".toRegex(), " ")
