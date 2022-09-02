package no.nav.personopplysninger.personalia.consumer.dto

import kotlinx.serialization.Serializable

@Serializable
data class Besoeksadresse(
    val type: String? = null,
    val postnummer: String? = null,
    val poststed: String? = null,
    val gatenavn: String? = null,
    val husnummer: String? = null,
    val husbokstav: String? = null
)
