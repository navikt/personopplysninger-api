package no.nav.personopplysninger.consumer.kodeverk.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.config.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class Betydning(
    @Serializable(with = LocalDateSerializer::class)
    val gyldigFra: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val gyldigTil: LocalDate,
    val beskrivelser: Map<String, Beskrivelse>
) {
    fun tekst(lang: String = NORSK_BOKMAAL): String? {
        return beskrivelser[lang]?.tekst
    }

    fun term(lang: String = NORSK_BOKMAAL): String? {
        return beskrivelser[lang]?.term
    }

    companion object {
        private const val NORSK_BOKMAAL = "nb"
    }
}