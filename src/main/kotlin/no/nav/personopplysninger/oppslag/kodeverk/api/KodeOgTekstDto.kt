package no.nav.personopplysninger.oppslag.kodeverk.api

data class KodeOgTekstDto(
        val kode: String,
        val tekst: String?
){
    companion object {
        fun fromKode(kode: Kode): KodeOgTekstDto {
            val enkeltBeskrivelse = kode.betydninger.first().beskrivelser.values.first().tekst
            return KodeOgTekstDto(kode.navn, enkeltBeskrivelse)
        }
    }
}
