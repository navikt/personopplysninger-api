package no.nav.personopplysninger.common.consumer.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
class GetKodeverkKoderBetydningerResponse {

    var betydninger: Map<String, List<Betydning>> = emptyMap()
        set(betydninger) {
            field = LinkedHashMap(betydninger)
        }

}
