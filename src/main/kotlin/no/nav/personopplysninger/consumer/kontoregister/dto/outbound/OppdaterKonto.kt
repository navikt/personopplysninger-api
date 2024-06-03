package no.nav.personopplysninger.consumer.kontoregister.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class OppdaterKonto(
    val kontohaver: String,
    val nyttKontonummer: String,
    val utenlandskKontoInfo: UtenlandskKontoInfo? = null
)
