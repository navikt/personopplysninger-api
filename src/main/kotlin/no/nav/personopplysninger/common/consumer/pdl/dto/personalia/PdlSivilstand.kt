package no.nav.personopplysninger.common.consumer.pdl.dto.personalia

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class PdlSivilstand(
    val type: PdlSivilstandstype,
    @Serializable(with = LocalDateSerializer::class)
    val gyldigFraOgMed: LocalDate? = null,
)