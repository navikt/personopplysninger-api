package no.nav.personopplysninger.kontaktinformasjon.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class SikkerDigitalPostkasse (
    val adresse: String? = null,
    val leverandoerAdresse: String? = null,
    val leverandoerSertifikat: String? = null,
)