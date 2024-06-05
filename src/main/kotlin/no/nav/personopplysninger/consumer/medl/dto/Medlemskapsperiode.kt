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
    val hjemmel: String,
    val trygdedekning: String? = null,
    val lovvalgsland: String? = null,
    val kilde: String,
    val studieinformasjon: Studieinformasjon? = null
)
