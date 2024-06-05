package no.nav.personopplysninger.consumer.kontoregister.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class OppdaterKonto(
    val kontohaver: String,
    val nyttKontonummer: String,
    val utenlandskKontoInfo: UtenlandskKontoInfo? = null
)
