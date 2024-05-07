package no.nav.personopplysninger.common.consumer.kodeverk.dto

import kotlinx.serialization.Serializable

@Serializable
class GetKodeverkKoderBetydningerResponse {
    val betydninger: Map<String, List<Betydning>> = emptyMap()
}
