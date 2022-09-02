package no.nav.personopplysninger.personalia.consumer.tpsproxy.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kontonummer (
    val datoFraOgMed: String? = null,
    val kilde: String? = null,
    val nummer: String? = null
)