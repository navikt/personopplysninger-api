package no.nav.personopplysninger.personalia.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Aapningstider(
    val id: String? = null,
    val dag: String? = null,
    val dato: String? = null,
    val stengt: String? = null,
    val fra: String? = null,
    val til: String? = null,
    val kommentar: String? = null
)
