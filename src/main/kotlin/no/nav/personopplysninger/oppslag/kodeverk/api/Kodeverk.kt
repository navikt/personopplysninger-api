package no.nav.personopplysninger.oppslag.kodeverk.api

import org.slf4j.LoggerFactory

class Kodeverk(
        val navn: String,
        val koder: List<Kode>
) {
    private val log = LoggerFactory.getLogger(Kodeverk::class.java)

    private val koderByNavn: Map<String, Kode> by lazy {
        koder.map { it.navn to it}.toMap()
    }

    fun getBetydninger(kodeNavn: String): List<Betydning> {
        return koderByNavn[kodeNavn]?.betydninger ?: emptyList()
    }

    fun term(kode: String?): String {
        if (kode.isNullOrEmpty() || getBetydninger(kode).isEmpty()) {
            return ""
        }
        try {
            return getBetydninger(kode)
                    .first()
                    .beskrivelser
                    .getValue("nb")
                    .term
        } catch (nse: NoSuchElementException) {
            log.warn("Oppslag på kodeverkstype $navn gav ingen treff for verdi $kode")
        }
        return kode
    }

    companion object {
        fun fromKoderBetydningerResponse(navn: String, response: GetKodeverkKoderBetydningerResponse): Kodeverk {
            return response.betydninger
                    .entries
                    .map { Kode(it.key, it.value) }
                    .let { koder -> Kodeverk(navn, koder) }
        }
    }
}
