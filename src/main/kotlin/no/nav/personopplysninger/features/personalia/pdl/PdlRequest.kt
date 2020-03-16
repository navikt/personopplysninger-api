package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.QueryVariables

interface PDLRequest {
    val variables: QueryVariables
    val query: String
}

fun String.compactJson(): String =
    trimIndent().replace("\r", " ").replace("\n", " ").replace("\\s+".toRegex(), " ")
