package no.nav.personopplysninger.consumer.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
class GetKodeverkKoderBetydningerResponse {

    var betydninger: Map<String, List<Betydning>> = emptyMap()
        set(betydninger) {
            field = LinkedHashMap(betydninger)
        }

}
