package no.nav.personopplysninger.common.consumer.pdl.request

interface PDLRequest {
    val variables: QueryVariables
    val query: String
}

fun String.compactJson(): String =
    trimIndent().replace("\r", " ").replace("\n", " ").replace("\\s+".toRegex(), " ")
