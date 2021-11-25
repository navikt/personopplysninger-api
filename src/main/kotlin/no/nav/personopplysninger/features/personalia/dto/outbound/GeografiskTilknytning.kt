package no.nav.personopplysninger.features.personalia.dto.outbound


data class GeografiskTilknytning(
        val bydel: String? = null,
        val kommune: String? = null,
        val land: String? = null,
        var enhet: String? = null
)