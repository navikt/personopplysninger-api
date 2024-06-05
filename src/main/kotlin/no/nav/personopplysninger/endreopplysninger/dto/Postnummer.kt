package no.nav.personopplysninger.endreopplysninger.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.consumer.kodeverk.dto.Betydning

@Serializable
data class Postnummer(
    val kode: String,
    val tekst: String? = null
) {
    companion object {
        fun fromBetydning(entry: Map.Entry<String, List<Betydning>>): Postnummer {
            return Postnummer(entry.key, entry.value.first().tekst())
        }
    }
}