package no.nav.personopplysninger.personalia.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Matrikkeladresse(

        val bruksnummer: String? = null,
        val festenummer: String? = null,
        val gaardsnummer: String? = null,
        val undernummer: String? = null
)