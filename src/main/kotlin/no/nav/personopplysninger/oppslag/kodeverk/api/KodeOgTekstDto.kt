package no.nav.personopplysninger.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KodeOgTekstDto(
        @JsonProperty("kode") val kode: String,
        @JsonProperty("tekst") val tekst: String
){
    companion object {
        fun fromKode(kode: Kode): KodeOgTekstDto {
            val enkeltBeskrivelse = kode.betydninger.first().beskrivelser.values.first().tekst
            return KodeOgTekstDto(kode.navn, enkeltBeskrivelse)
        }
    }
}
