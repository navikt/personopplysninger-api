package no.nav.personopplysninger.consumer.medl.dto

import kotlinx.serialization.Serializable

@Serializable
data class Studieinformasjon(
    val statsborgerland: String,
    val studieland: String?,
)
