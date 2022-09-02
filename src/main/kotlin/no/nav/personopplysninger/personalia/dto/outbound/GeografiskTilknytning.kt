package no.nav.personopplysninger.personalia.dto.outbound

import kotlinx.serialization.Serializable


@Serializable
data class GeografiskTilknytning(
        val bydel: String? = null,
        val kommune: String? = null,
        val land: String? = null,
        var enhet: String? = null
)