package no.nav.personopplysninger.features.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class GetKodeverkKoderBetydningerResponse {

    var betydninger: Map<String, List<Betydning>> = emptyMap()
        set(betydninger) {
            field = LinkedHashMap(betydninger)
        }

}
