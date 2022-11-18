package no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class HentAktivKonto(
    val kontohaver: String
)
