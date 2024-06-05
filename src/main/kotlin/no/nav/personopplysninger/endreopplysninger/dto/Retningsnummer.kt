package no.nav.personopplysninger.endreopplysninger.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.consumer.kodeverk.dto.Betydning

@Serializable
data class Retningsnummer(
    val landskode: String,
    val land: String?
) {

    companion object {
        fun fromBetydning(entry: Map.Entry<String, List<Betydning>>): Retningsnummer {
            return Retningsnummer(
                landskode = entry.key,
                land = entry.value.first().tekst()
            )
        }
    }
}
