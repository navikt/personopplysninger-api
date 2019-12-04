package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Substatus {
    val beskrivelse: String? = null
    val domene: String? = null
    val kode: String? = null
    val referanse: String? = null
    val status: String? = null
}
