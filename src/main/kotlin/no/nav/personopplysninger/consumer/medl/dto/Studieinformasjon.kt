package no.nav.personopplysninger.consumer.medl.dto

import kotlinx.serialization.Serializable

@Serializable
data class Studieinformasjon(
    var statsborgerland: String,
    var studieland: String?,
)
