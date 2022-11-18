package no.nav.personopplysninger.common.consumer.kodeverk.dto

import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
class Kodeverk(
    val navn: String,
    val koder: List<Kode>
) {
    private val log = LoggerFactory.getLogger(Kodeverk::class.java)

    private val koderByNavn: Map<String, Kode> by lazy {
        koder.associateBy { it.navn }
    }

    private fun getBetydninger(kodeNavn: String): List<Betydning> {
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
            log.warn("Oppslag på kodeverkstype $navn gav ingen term-treff for verdi $kode")
        }
        return kode
    }

    fun tekst(kode: String?): String {
        if (kode.isNullOrEmpty() || getBetydninger(kode).isEmpty()) {
            return ""
        }
        try {
            return getBetydninger(kode)
                .first()
                .beskrivelser
                .getValue("nb")
                .tekst ?: term(kode)
        } catch (nse: NoSuchElementException) {
            log.warn("Oppslag på kodeverkstype $navn gav ingen tekst-treff for verdi $kode")
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
