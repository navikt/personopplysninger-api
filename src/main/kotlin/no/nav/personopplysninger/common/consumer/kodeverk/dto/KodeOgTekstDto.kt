package no.nav.personopplysninger.common.consumer.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
data class KodeOgTekstDto(
    val kode: String,
    val tekst: String? = null
) {
    companion object {
        fun fromKode(kode: Kode): KodeOgTekstDto {
            val enkeltBeskrivelse = kode.betydninger.first().beskrivelser.values.first().tekst
            return KodeOgTekstDto(kode.navn, enkeltBeskrivelse)
        }
    }
}
