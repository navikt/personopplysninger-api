package no.nav.personopplysninger.personalia.consumer.tpsproxy.dto

import kotlinx.serialization.Serializable

@Serializable
data class Personinfo(
    val kontonummer: Kontonummer? = null,
    val utenlandskBank: UtenlandskBank? = null,
)