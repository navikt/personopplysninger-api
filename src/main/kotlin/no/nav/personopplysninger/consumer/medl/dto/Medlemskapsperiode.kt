package no.nav.personopplysninger.consumer.medl.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class Medlemskapsperiode(
    @Serializable(with = LocalDateSerializer::class)
    val fraOgMed: LocalDate,
    var hjemmel: String,
    val kilde: String,
    var lovvalgsland: String? = null,
    val medlem: Boolean,
    var studieinformasjon: Studieinformasjon? = null,
    @Serializable(with = LocalDateSerializer::class)
    val tilOgMed: LocalDate? = null,
    var trygdedekning: String? = null
)
