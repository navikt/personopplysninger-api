package no.nav.personopplysninger.medl.dto

import kotlinx.serialization.Serializable

@Serializable
data class Studieinformasjon(
    var statsborgerland: String,
    var studieland: String
)
