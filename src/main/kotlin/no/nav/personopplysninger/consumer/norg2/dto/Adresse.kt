package no.nav.personopplysninger.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Adresse(
    val postnummer: String?,
    val poststed: String?,
    val gatenavn: String?,
    val husnummer: String?,
    val husbokstav: String?,
    val adresseTilleggsnavn: String?,
)