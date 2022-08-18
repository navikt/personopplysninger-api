package no.nav.personopplysninger.consumer.pdl.request

import kotlinx.serialization.Serializable

@Serializable
data class QueryVariables (
        val ident: String
)