package no.nav.personopplysninger.common.pdl.dto.personalia

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class PdlStatsborgerskap(
    val land: String,
    @Serializable(with = LocalDateSerializer::class)
    val gyldigTilOgMed: LocalDate? = null
)