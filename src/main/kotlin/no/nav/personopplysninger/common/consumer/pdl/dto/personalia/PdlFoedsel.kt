package no.nav.personopplysninger.common.consumer.pdl.dto.personalia

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class PdlFoedsel(
    @Serializable(with = LocalDateSerializer::class)
    val foedselsdato: LocalDate? = null,
    val foedested: String? = null,
    val foedekommune: String? = null,
    val foedeland: String? = null,
)