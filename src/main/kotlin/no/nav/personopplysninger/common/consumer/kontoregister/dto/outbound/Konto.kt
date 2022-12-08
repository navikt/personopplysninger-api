package no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Konto(
    val kontonummer: String? = null,
    val utenlandskKontoInfo: UtenlandskKontoInfo? = null,
    val error: Boolean = false
)
