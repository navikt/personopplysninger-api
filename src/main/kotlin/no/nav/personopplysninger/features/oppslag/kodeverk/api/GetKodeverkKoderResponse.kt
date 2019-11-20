package no.nav.personopplysninger.features.oppslag.kodeverk.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class GetKodeverkKoderResponse {
    val koder = ArrayList<String>()

    constructor() {}
    constructor(koder: List<String>) {
        this.koder.addAll(koder)
    }
}
