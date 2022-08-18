package no.nav.personopplysninger.consumer.pdl.dto.personalia

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class PdlSivilstand(
    val type: PdlSivilstandstype,
    @Serializable(with = LocalDateSerializer::class)
    val gyldigFraOgMed: LocalDate? = null,
)