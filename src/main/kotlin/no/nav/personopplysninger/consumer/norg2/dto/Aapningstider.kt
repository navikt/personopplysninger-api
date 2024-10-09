package no.nav.personopplysninger.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Aapningstider(
    val dag: String?,
    val dato: String?,
    val fra: String?,
    val til: String?,
    val kommentar: String?,
    val stengt: String?,
    val kunTimeavtale: String?,
)