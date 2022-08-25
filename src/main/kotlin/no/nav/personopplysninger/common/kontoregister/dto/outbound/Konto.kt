package no.nav.personopplysninger.common.kontoregister.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Konto(
    val kontonummer: String,
    val utenlandskKontoInfo: UtenlandskKontoInfo? = null
)
