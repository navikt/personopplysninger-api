package no.nav.personopplysninger.consumer.medl.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class Medlemskapsperiode(
    @Serializable(with = LocalDateSerializer::class)
    val fraOgMed: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val tilOgMed: LocalDate,
    val medlem: Boolean,
    var hjemmel: String,
    var trygdedekning: String? = null,
    var lovvalgsland: String? = null,
    val kilde: String,
    var studieinformasjon: Studieinformasjon? = null
)
