package no.nav.personopplysninger.personalia.dto.outbound

import kotlinx.serialization.Serializable


@Serializable
data class Aapningstid(
    val dag: String? = null,
    val dato: String? = null,
    val fra: String? = null,
    val til: String? = null,
    val stengt: String? = null,
    val kommentar: String? = null
)