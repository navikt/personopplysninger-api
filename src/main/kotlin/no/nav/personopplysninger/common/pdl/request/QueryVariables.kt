package no.nav.personopplysninger.common.pdl.request

import kotlinx.serialization.Serializable

@Serializable
data class QueryVariables (
        val ident: String
)