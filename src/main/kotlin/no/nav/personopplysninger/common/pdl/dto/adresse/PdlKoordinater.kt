package no.nav.personopplysninger.common.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlKoordinater(
        val x: Double? = null,
        val y: Double? = null,
        val z: Double? = null,
        val kvalitet: Int? = null
)