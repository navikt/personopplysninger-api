package no.nav.personopplysninger.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Norg2EnhetKontaktinfo(
    val navn: String,
    val brukerkontakt: Brukerkontakt,
)