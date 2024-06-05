package no.nav.personopplysninger.consumer.digdirkrr.dto

import kotlinx.serialization.Serializable

@Serializable
data class SikkerDigitalPostkasse (
    val adresse: String? = null,
    val leverandoerAdresse: String? = null,
    val leverandoerSertifikat: String? = null,
)